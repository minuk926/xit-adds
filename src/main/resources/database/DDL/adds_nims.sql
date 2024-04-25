create or replace table tb_dsuse_mgt
(
    dscdmng_id    varchar(10) not null comment '폐기관리_id'
        primary key,
    user_id       varchar(10) null comment '사용자_아이디',
    usr_rpt_id_no varchar(30) NOT NULL COMMENT '사용자_보고_식별_번호',
    org_usr_rpt_id_no varchar(30) NOT NULL COMMENT '원_사용자_보고_식별_번호',
    bssh_cd       varchar(10) null comment '마약류취급자식별_번호',
    prgrs_stts_cd varchar(10) null comment '진행_상태_코드',
    use_yn        varchar(1)  not null comment '사용_여부',
    reg_dt        varchar(14) not null comment '등록_일시',
    rgtr          varchar(10) not null comment '등록자',
    mdfcn_dt      varchar(14) null comment '수정_일시',
    mdfr          varchar(10) null comment '수정자',
    constraint idx_tb_dsuse_mgt_01
        unique (org_usr_rpt_id_no, usr_rpt_id_no)
)
    comment '폐기관리';

CREATE TABLE tb_dsuse_mgt
(
    dscdmng_id      varchar(10)  NOT NULL COMMENT '폐기관리_id',
    usr_rpt_id_no   varchar(30)  NOT NULL COMMENT '사용자_보고_식별_번호',
    user_id         varchar(10)  NULL     COMMENT '사용자_아이디',
    prgrs_stts_cd   varchar(10)  NULL     COMMENT '진행_상태_코드',
    use_yn          varchar(1)   NOT NULL COMMENT '사용_여부',
    reg_dt          varchar(14)  NOT NULL COMMENT '등록_일시',
    rgtr            varchar(10)  NOT NULL COMMENT '등록자',
    mdfcn_dt        varchar(14)  NULL     COMMENT '수정_일시',
    mdfr            varchar(10)  NULL     COMMENT '수정자',
    PRIMARY KEY (dscdmng_id, usr_rpt_id_no)
) COMMENT '폐기관리';

CREATE TABLE tb_dsuse_rpt_info
(
    usr_rpt_id_no   varchar(30)  NOT NULL COMMENT '사용자_보고_식별_번호',
    ref_usr_rpt_id_no varchar(30)  NULL COMMENT '참조_사용자_보고_식별_번호',
    bssh_cd         varchar(10)  NOT NULL COMMENT '마약류취급자식별_번호',
    bssh_nm         varchar(120) NOT NULL COMMENT '업체_명',
    induty_nm       varchar(200) NOT NULL COMMENT '업종_명',
    rpt_ty_cd       varchar(1)   NULL     COMMENT '보고_유형_코드(0-신규,1-취소,2-변경)',
    rnd_dtl_rpt_cnt decimal      NULL     COMMENT '수불_상세_보고_수',
    hdr_de          varchar(8)   NULL     COMMENT '취급_일자',
    rpt_de          varchar(8)   NULL     COMMENT '보고_일자',
    dsuse_se_cd     varchar(10)  NULL     COMMENT '폐기_구분_코드',
    dsuse_prv_cd    varchar(10)  NULL     COMMENT '폐기_사유_코드',
    dsuse_mth_cd    varchar(10)  NULL     COMMENT '폐기_방법_코드',
    dsuse_loc       varchar(120) NULL     COMMENT '폐기_장소',
    dsuse_de        varchar(80)  NULL     COMMENT '폐기_일자',
    status          varchar(1)   NULL     COMMENT '처리상태(0-정상,1-취소,2-변경)',
    rpt_prg_stts_cd varchar(10)  NULL     COMMENT '보고_진행_상태_코드',
    org_usr_rpt_id_no varchar(30)  null comment '원_사용자_보고_식별_번호',
    use_yn          varchar(1)   NOT NULL COMMENT '사용_여부',
    reg_dt          varchar(14)  NOT NULL COMMENT '등록_일시',
    rgtr            varchar(10)  NOT NULL COMMENT '등록자',
    mdfcn_dt        varchar(14)  NULL     COMMENT '수정_일시',
    mdfr            varchar(10)  NULL     COMMENT '수정자',
    PRIMARY KEY (usr_rpt_id_no)
) COMMENT '폐기_보고_정보';
create or replace index idx_tb_dsuse_rpt_info_01
    on tb_dsuse_rpt_info (org_usr_rpt_id_no);

CREATE TABLE tb_dsuse_rpt_info_dtl
(
    usr_rpt_id_no   varchar(30)   NOT NULL COMMENT '사용자_보고_식별_번호',
    usr_rpt_ln_id_no varchar(35)  NOT NULL COMMENT '사용자_보고_라인_식별_번호',
    prduct_cd        varchar(14)  NOT NULL COMMENT '제품_코드',
    prduct_nm        varchar(300) NOT NULL COMMENT '제품_명',
    min_distb_qy     decimal      NULL     COMMENT '최소_유통단위_수량',
    pce_qy           decimal      NULL     COMMENT '낱개단위_수량',
    mnf_no           varchar(20)  NULL     COMMENT '제조_번호',
    prd_valid_de     varchar(8)   NULL     COMMENT '제품_유효기한_일자',
    mnf_seq          varchar(42)  NULL     COMMENT '제조_일련번호',
    mvmn_ty_cd       varchar(4)   NOT NULL COMMENT '이동_유형_코드',
    dsuse_qy         decimal      NOT NULL COMMENT '폐기_수량',
    use_yn           varchar(1)   NOT NULL COMMENT '사용_여부',
    reg_dt           varchar(14)  NOT NULL COMMENT '등록_일시',
    rgtr             varchar(10)  NOT NULL COMMENT '등록자',
    mdfcn_dt         varchar(14)  NULL     COMMENT '수정_일시',
    mdfr             varchar(10)  NULL     COMMENT '수정자',
    PRIMARY KEY (usr_rpt_id_no, usr_rpt_ln_id_no),
    constraint fk_dsuse_rpt_info_dtl_to_dsuse_rpt_info
        foreign key (usr_rpt_id_no) references tb_dsuse_mgt (usr_rpt_id_no)
) COMMENT '폐기_보고_정보_상세';

CREATE TABLE tb_bssh_info
(
    bssh_cd      varchar(10)  NOT NULL COMMENT '마약류취급자식별_번호',
    bssh_nm      varchar(120) NULL     COMMENT '업체_명',
    induty_nm    varchar(200) NULL     COMMENT '업종_명',
    hdnt_cd      varchar(10)  NULL     COMMENT '의료업자_구분',
    hdnt_nm      varchar(200) NULL     COMMENT '의료업자구분_명',
    bizrno       varchar(10)  NULL     COMMENT '사업자등록번호',
    rprsntv_nm   varchar(60)  NULL     COMMENT '대표자_명',
    chrg_nm      varchar(60)  NULL     COMMENT '담당자_명',
    hptl_no      varchar(20)  NULL     COMMENT '요양기관_기호',
    join_yn      varchar(200) NULL     COMMENT '회원가입_여부',
    bssh_stts_nm varchar(200) NULL     COMMENT '상태',
    prmisn_no    varchar(40)  NULL     COMMENT '허가_번호',
    use_yn       varchar(1)   NOT NULL COMMENT '사용_여부',
    reg_dt       varchar(14)  NOT NULL COMMENT '등록_일시',
    rgtr         varchar(10)  NOT NULL COMMENT '등록자',
    mdfcn_dt     varchar(14)  NULL     COMMENT '수정_일시',
    mdfr         varchar(10)  NULL     COMMENT '수정자',
    PRIMARY KEY (bssh_cd)

) COMMENT '취급자_정보';


CREATE TABLE tb_prduct_info
(
    prduct_cd          varchar(14)  NOT NULL COMMENT '제품_코드',
    prdlst_mst_cd      varchar(14)  NULL     COMMENT '제품_대표_코드',
    prduct_nm          varchar(300) NULL     COMMENT '품목_명',
    nrcd_se_nm         varchar(50)  NULL     COMMENT '마약/항정_구분_명',
    prtm_se_nm         varchar(200) NULL     COMMENT '중점/일반_구분',
    prd_min_distb_qy   int          NULL     COMMENT '제품_최소_유통단위_수량',
    std_packng_stle_nm varchar(200) NULL     COMMENT '제품_최소_유통단위',
    prd_tot_pce_qy     int          NULL     COMMENT '제품_총_낱개단위_수량',
    pce_co_unit_nm     varchar(200) NULL     COMMENT '제품_낱개_단위_명',
    bssh_cd            varchar(10)  NULL     COMMENT '마약류취급자식별_번호',
    rgs_dt             date         NULL     COMMENT '등록_일',
    upd_dt             date         NULL     COMMENT '변경_일',
    use_yn             varchar(1)   NOT NULL COMMENT '사용_여부',
    reg_dt             varchar(14)  NOT NULL COMMENT '등록_일시',
    rgtr               varchar(10)  NOT NULL COMMENT '등록자',
    mdfcn_dt           varchar(14)  NULL     COMMENT '수정_일시',
    mdfr               varchar(10)  NULL     COMMENT '수정자',
    PRIMARY KEY (prduct_cd)
) COMMENT '품목_정보';

CREATE TABLE tb_storge_info
(
    bssh_cd        varchar(10)  NOT NULL COMMENT '마약류취급자식별_번호',
    bssh_nm        varchar(120) NULL     COMMENT '업체_명',
    use_at         char         NULL     COMMENT '사용_유무',
    storge_se_nm   varchar(200) NULL     COMMENT '저장소_유형',
    storge_no      varchar(16)  NULL     COMMENT '저장소_번호',
    storge_nm      varchar(100) NULL     COMMENT '저장소_명',
    bass_adres     varchar(200) NULL     COMMENT '기본_주소',
    bass_dtl_adres varchar(200) NULL     COMMENT '상세_주소',
    use_yn         varchar(1)   NOT NULL COMMENT '사용_여부',
    reg_dt         varchar(14)  NOT NULL COMMENT '등록_일시',
    rgtr           varchar(10)  NOT NULL COMMENT '등록자',
    mdfcn_dt       varchar(14)  NULL     COMMENT '수정_일시',
    mdfr           varchar(10)  NULL     COMMENT '수정자',
    PRIMARY KEY (bssh_cd)
) COMMENT '저장소_정보';

CREATE TABLE tb_jrdt_gov_info
(
  bssh_cd        varchar(10)  NOT NULL COMMENT '마약류취급자식별_번호',
  of_cd          varchar(10)  NULL     COMMENT '기관_코드',
  of_nm          varchar(100) NULL     COMMENT '기관_명',
  up_of_nm       varchar(100) NULL     COMMENT '상위_기관_명',
  top_of_nm      varchar(100) NULL     COMMENT '최상위_기관_명',
  bass_adres     varchar(200) NULL     COMMENT '기본_주소',
  bass_dtl_adres varchar(200) NULL     COMMENT '상세_주소',
  use_yn         varchar(1)   NOT NULL COMMENT '사용_여부',
  reg_dt         varchar(14)  NOT NULL COMMENT '등록_일시',
  rgtr           varchar(10)  NOT NULL COMMENT '등록자',
  mdfcn_dt       varchar(14)  NULL     COMMENT '수정_일시',
  mdfr           varchar(10)  NULL     COMMENT '수정자',
  PRIMARY KEY (bssh_cd)
) COMMENT '관할_관청_정보';

CREATE TABLE tb_cmm_api_log
(
    request_id    varchar(40)   NOT NULL COMMENT '요청 id',
    system_id     varchar(20)   NULL     COMMENT '시스템 id(ens|fims등)',
    req_system_id varchar(20)   NULL     COMMENT '요청시스템 id(kakao|kt등)',
    method        varchar(10)   NULL     COMMENT '메소드(get|put|post|delete)',
    uri           varchar(255)  NULL     COMMENT '호출 uri',
    success       varchar(5)    NULL     COMMENT '성공/실패(true|false)',
    param         longtext      NULL     COMMENT '파라메터',
    response      longtext      NULL     COMMENT '호출 결과',
    message       longtext      NULL     COMMENT '메세지(에러메세지)',
    ip            varchar(30)   NULL     COMMENT 'ip',
    access_token  varchar(2000) NULL     COMMENT '토큰',
    session_id    varchar(255)  NULL     COMMENT '세션id',
    updt_dt       datetime(3)   NULL     COMMENT '변경일시(now(3)-밀리세컨드까지)',
    updt_id       varchar(20)   NULL     COMMENT '변경자',
    regist_dt     datetime(3)   NULL     COMMENT '생성일시(now(3)-밀리세컨드까지)',
    regist_id     varchar(20)   NULL     COMMENT '생성자',
    PRIMARY KEY (request_id)
) COMMENT 'api 호출 로그';

CREATE TABLE tb_cmm_refresh_token
(
    id            varchar(20)  NOT NULL COMMENT 'id',
    refresh_token varchar(256) NOT NULL COMMENT 'refreshtoken',
    regist_dt     timestamp    NULL     COMMENT '등록일시',
    updt_dt       timestamp    NULL     COMMENT '변경일시',
    PRIMARY KEY (id)
) COMMENT '리프레쉬토큰';


CREATE TABLE tb_purchase_info
(
  rptr_entrps_nm     varchar(120) NULL     COMMENT '판매_업체_명',
  prduct_nm          varchar(300) NULL     COMMENT '제품_명',
  hdr_de             char(8)      NULL     COMMENT '취급_일자',
  mnf_no             varchar(20)  NULL     COMMENT '제조_번호',
  mnf_seq            varchar(42)  NULL     COMMENT '제조_일련번호',
  min_distb_qy       decimal      NULL     COMMENT '최소_유통단위_수량',
  prd_min_distb_unit varchar(10)  NULL     COMMENT '제품_최소_유통_단위',
  prd_min_distb_qy   decimal      NULL     COMMENT '제품_최소_유통단위_수량',
  pce_qy             decimal      NULL     COMMENT '낱개단위_수량',
  prd_tot_pce_qy     decimal      NULL     COMMENT '제품_총_낱개단위_수량',
  prd_pce_unit       varchar(10)  NULL     COMMENT '제품_낱개_단위',
  storge_no          varchar(16)  NULL     COMMENT '저장소_번호',
  storge_nm          varchar(100) NULL     COMMENT '저장소_명',
  bizrno             varchar(10)  NULL     COMMENT '사업자등록번호',
  prd_sgtin          varchar(68)  NULL     COMMENT '제품_바코드',
  prd_valid_de       varchar(8)   NULL     COMMENT '제품_유효기한_일자',
  pcm_rpt_at         varchar(1)   NULL     COMMENT '구입보고존재여부',
  usr_rpt_id_no      varchar(30)  NULL     COMMENT '판매사용자_보고_식별_번호',
  chrg_nm            varchar(120) NULL     COMMENT '판매담당자_명',
  chrg_tel_no        varchar(60)  NULL     COMMENT '판매담당자_전화_번호',
  ref_usr_rpt_id_no  varchar(22)  NULL     COMMENT '참조_사용자_보고_식별_번호',
  prtm_se_nm         varchar(200) NULL     COMMENT '중점/일반_구분',
  rpt_de             char(8)      NULL     COMMENT '보고_일자',
  rpt_ty_nm          varchar(1)   NULL     COMMENT '보고_유형_명',
  induty_cd_nm       varchar(200) NULL     COMMENT '판매업체업종_명',
  bssh_cd            varchar(10)  NOT NULL COMMENT '판매업체식별_번호',
  prduct_cd          varchar(14)  NOT NULL COMMENT '제품_코드',
  use_yn             varchar(1)   NOT NULL,
  reg_dt             varchar(14)  NOT NULL,
  rgtr               varchar(10)  NOT NULL,
  mdfcn_dt           varchar(14)  NULL    ,
  mdfr               varchar(10)  NULL    ,
  PRIMARY KEY (bssh_cd, prduct_cd)
) COMMENT '구입대상_정보';

CREATE TABLE tb_stock_info
(
  storge_no          varchar(16)  NOT NULL COMMENT '저장소_번호',
  prduct_cd          varchar(14)  NOT NULL COMMENT '제품_코드',
  storge_nm          varchar(100) NULL     COMMENT '저장소_명',
  prduct_nm          varchar(300) NULL     COMMENT '제품_명',
  mnf_no             varchar(20)  NULL     COMMENT '제조_번호',
  prd_valid_de       char(8)      NULL     COMMENT '제품_유효_기한',
  mnf_seq            varchar(42)  NULL     COMMENT '제조_일련번호',
  prd_min_distb_unit varchar(100) NULL     COMMENT '제품_최소_유통_단위',
  prd_tot_pce_qy     int          NULL     COMMENT '제품_총_낱개단위_수량',
  pce_invt_qy        decimal      NULL     COMMENT '낱개단위_재고_수량',
  prd_pce_unit       varchar(100) NULL     COMMENT '제품_낱개_단위',
  prtm_se_cd         varchar(200) NULL     COMMENT '중점/일반관리_구분_코드',
  storge_se_nm       varchar(200) NULL     COMMENT '저장소_구분_명',
  min_distb_invt_qy  decimal      NULL     COMMENT '최소_유통_단위재고_수량',
  hdr_de             char(8)      NULL     COMMENT '취급_일자',
  ddln_ym            char(8)      NULL     COMMENT '재고기준_년월',
  use_yn             varchar(1)   NOT NULL,
  reg_dt             varchar(14)  NOT NULL,
  rgtr               varchar(10)  NOT NULL,
  mdfcn_dt           varchar(14)  NULL    ,
  mdfr               varchar(10)  NULL    ,
  PRIMARY KEY (storge_no, prduct_cd)
) COMMENT '재고_정보';


