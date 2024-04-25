select *
from tb_dsuse_mgt;

select *
from tb_dsuse_mgt_dtl;

select *
  from tb_dsuse_mgt tdm
  join tb_dsuse_mgt_dtl tdmd
    on tdm.dscdmng_id = tdmd.dscdmng_id;

select *
from tb_prduct_info
where prduct_cd in( '8806498014732', '8806718050823');

select *
from tb_bssh_info
where 1=1
-- and bssh_cd = 'H00099001'
and bssh_nm like '수지미래%';

select *
from tb_storge_info
where bssh_cd = 'H00099001';

select tdm.dscdmng_id                       /* 폐기관리ID */
     , '' AS uid                            /* 보고자식별ID */
     , '' AS rndRmk                         /* 수불비고 */
     , tdm.bssh_cd                          /* 마약류취급자식별번호 */
     , '' AS rptSeCd                        /* 보고구분코드 : AAR-폐기 */
     , CONCAT(tdm.dscdmng_id, '_00001') AS usrRptIdNo         /* 사용자보고식별번호 */
     , '' AS refUsrRptIdNo                  /* 참조사용자식별번호 */
     , '0' AS rptTyCd                       /* 보고유형코드 : 0-신규, 1-취소, 2-변형 */
     , '' AS rmk                            /* 비고 : 취소 및 변경시 사유 필수 기재 */
     , '' AS rptrNm                         /* 보고자명 : NIMS 등록 */
     , '' AS rptrEntrpsNm                   /* 보고업체명 : NIMS 등록 */
     , '' AS chrgNm                         /* 담당자명 : 없는 경우 보고자명 */
     , '' AS chrgTelNo                      /* 담당자전화번호 */
     , '' AS chrgMpNo                       /* 담당자휴대폰번호 - 암호화 */
     , '' AS rndDtlRptCnt                   /* 수불상세보고수 */
     , '1' AS dsuseSeCd                     /* 폐기구분코드 */
     , '' AS dsusePrvCd    /* 폐기사유코드 */
     , '3' AS dsuseMthCd                    /* 폐기방법코드 */
     , '' AS dsuseLoc                       /* 폐기장소 */
     , '' AS dsuseDe                        /* 폐기일자 */
     , '' AS dsuseInsttCd                   /* 폐기관할기관코드 */
     , '0' AS atchFileCo                    /* 첨부파일수 */
     , '' AS registerId                     /* 등록자ID - NIMS 등록 */
     , '' AS fileCreatDt                    /* 파일생성일시 */
     , 'Y' AS useYn
     , 'nims-api' AS rgtr
  from tb_dsuse_mgt tdm
 where tdm.dscdmng_id = '2024040002';

select tdm.dscdmng_id                       /* 폐기관리ID */
     , '' AS uid                            /* 보고자식별ID */
     , tdmd.dscdmng_sn                      /* 폐기관리순번 */
     , '' AS usrRptLnIdNo                   /* 사용자보고라인식별번호 */
     , tsi.storge_no AS storgeNo                 /* 저장소번호 */
     , '1102' AS mvmnTyCd                   /* 이동유형코드 */
     , tpi.prduct_cd                        /* 제품코드 */
     , '' AS mnfNo                          /* 제조번호 */
     , '' AS mnfSeq                         /* 제품일련번호 */
     , '' AS minDistbQy                     /* 제품유통단위수량 */
     , '' AS prdMinDistbUnit                /* 제품최소유통단위 */
     , '' AS pceQy                          /* 낱개단위수량 */
     , '' AS prdPceUnit                     /* 제품낱개단위 */
     , tpi.prduct_nm                        /* 제품명 */
     , '' AS prdSgtin                       /* 제품바코드(RFID) */
     , tpi.prd_min_distb_qy                 /* 제품최소유통단위수량 */
     , tpi.prd_tot_pce_qy                   /* 제품총낱개단위수량 */
     , '' AS prdValidDe                     /* 제품유효기한일자 */
     , '' AS fileCreatDt                    /* 파일생성일시 */
     , 'Y' AS useYn
     , 'nims-api' AS rgtr
  from tb_dsuse_mgt tdm
  join tb_dsuse_mgt_dtl tdmd
    on tdm.dscdmng_id = tdmd.dscdmng_id
  join tb_prduct_info tpi
    on tdmd.prduct_cd = tpi.prduct_cd
  left outer join tb_storge_info tsi
    on tdm.bssh_cd = tsi.bssh_cd
 where tdm.dscdmng_id = '2024040001';

select *
from tb_dsuse_rpt_info
where use_yn = 'Y';

select tdm.dscdmng_id
     , tdm.user_id
     , tdri.*
     , tbi.rprsntv_nm
     , tbi.prmisn_no
  from tb_dsuse_mgt tdm
  join tb_dsuse_rpt_info tdri
    on (tdm.org_usr_rpt_id_no = tdri.org_usr_rpt_id_no
   and tdm.usr_rpt_id_no = tdri.usr_rpt_id_no)
  left outer join tb_bssh_info tbi
    on tdri.bssh_cd = tbi.bssh_cd







WITH RECURSIVE temp (
    usr_rpt_id_no, rpt_ty_cd, ref_usr_rpt_id_no, depth, path
) AS (select usr_rpt_id_no
           , rpt_ty_cd
           , ref_usr_rpt_id_no
           , 0
           , usr_rpt_id_no
      from tb_dsuse_rpt_info
      where 1=1
        -- and use_yn = 'Y'
        and ref_usr_rpt_id_no is null
      UNION ALL
      select tdri.usr_rpt_id_no
           , tdri.rpt_ty_cd
           , tdri.ref_usr_rpt_id_no
           , tgt.depth + 1
           , concat(tgt.path, ',', tdri.usr_rpt_id_no)
      from tb_dsuse_rpt_info tdri
               INNER JOIN temp tgt
                          ON tdri.ref_usr_rpt_id_no = tgt.usr_rpt_id_no
    )
     select usr_rpt_id_no
           , rpt_ty_cd
           , ref_usr_rpt_id_no
     , depth
     , path
from temp
order by temp.path;

