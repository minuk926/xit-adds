
create table tb_bssh_info
(
    bssh_cd              varchar(10) not null,
    bssh_nm              varchar(120) null,
    induty_nm            varchar(200) null,
    hdnt_cd              varchar(10) null,
    hdnt_nm              varchar(200) null,
    bizrno               varchar(10) null,
    rprsntv_nm           varchar(60) null,
    chrg_nm              varchar(60) null,
    hptl_no              varchar(20) null,
    join_yn              varchar(200) null,
    bssh_stts_nm         varchar(200) null,
    prmisn_no            varchar(40) null,
    use_yn               varchar(1) not null,
    reg_dt               varchar(14) not null,
    rgtr                 varchar(10) not null,
    mdfcn_dt             varchar(14) null,
    mdfr                 varchar(10) null
);

alter table tb_bssh_info
    add primary key (bssh_cd);

create table tb_dsuse_mgt
(
    dscdmng_id           varchar(10) not null,
    user_id              varchar(6) null,
    bssh_cd              varchar(10) not null,
    prgrs_stts_cd        varchar(10) null,
    rpt_rcept_no         varchar(30) null,
    use_yn               varchar(1) not null,
    reg_dt               varchar(14) not null,
    rgtr                 varchar(10) not null,
    mdfcn_dt             varchar(14) null,
    mdfr                 varchar(10) null
);

alter table tb_dsuse_mgt
    add primary key (dscdmng_id);

create table tb_dsuse_mgt_dtl
(
    dscdmng_id           varchar(10) not null,
    dscdmng_sn           varchar(3) not null,
    prdct_cd             varchar(14) not null,
    dsuse_qy             numeric not null,
    acdnt_ocrn_day       char(8) not null,
    ocrn_rpt_day         char(8) not null,
    acdnt_ocrn_rsn       varchar(30) not null,
    use_yn               varchar(1) not null,
    reg_dt               varchar(14) not null,
    rgtr                 varchar(10) not null,
    mdfcn_dt             varchar(14) null,
    mdfr                 varchar(10) null
);

alter table tb_dsuse_mgt_dtl
    add primary key (dscdmng_id,dscdmng_sn);

create table tb_dsuse_rpt
(
    dscdmng_id           varchar(10) not null,
    uid                  varchar(128) not null,
    rnd_rmk              varchar(4000) null,
    hdr_de               varchar(8) null,
    bssh_cd              varchar(10) null,
    rpt_se_cd            varchar(3) null,
    usr_rpt_id_no        varchar(30) null,
    ref_usr_rpt_id_no    varchar(30) null,
    rpt_ty_cd            char(1) null,
    rmk                  varchar(2000) null,
    rptr_nm              varchar(60) null,
    rptr_entrps_nm       varchar(120) null,
    chrg_nm              varchar(120) null,
    chrg_tel_no          varchar(60) null,
    chrg_mp_no           varchar(60) null,
    rnd_dtl_rpt_cnt      numeric null,
    dsuse_se_cd          varchar(10) null,
    dsuse_prv_cd         varchar(10) null,
    dsuse_mth_cd         varchar(10) null,
    dsuse_loc            varchar(120) null,
    dsuse_de             varchar(8) null,
    dsuse_instt_cd       varchar(7) null,
    atch_file_co         numeric null,
    register_id          varchar(20) null,
    file_creat_dt        varchar(14) null,
    use_yn               varchar(1) not null,
    reg_dt               varchar(14) not null,
    rgtr                 varchar(10) not null,
    mdfcn_dt             varchar(14) null,
    mdfr                 varchar(10) null
);

alter table tb_dsuse_rpt
    add primary key (dscdmng_id, uid);

create table tb_dsuse_rpt_dtl
(
    dscdmng_id           varchar(10) not null,
    uid                  varchar(128) not null,
    dscdmng_sn           varchar(3) not null,
    usr_rpt_ln_id_no     varchar(35) not null,
    storge_no            varchar(16) null,
    mvmn_ty_cd           varchar(4) null,
    prduct_cd            varchar(14) null,
    mnf_no               varchar(20) null,
    mnf_seq              varchar(42) null,
    min_distb_qy         numeric null,
    prd_min_distb_unit   varchar(10) null,
    pce_qy               numeric null,
    prd_pce_unit         varchar(10) null,
    prduct_nm            varchar(300) null,
    prd_sgtin            varchar(68) null,
    prd_min_distb_qy     numeric null,
    prdct_tot_pce_qy     numeric null,
    prd_valid_de         varchar(8) null,
    file_creat_dt        varchar(14) null,
    dsuse_qy             numeric null,
    use_yn               varchar(1) not null,
    reg_dt               varchar(14) not null,
    rgtr                 varchar(10) not null,
    mdfcn_dt             varchar(14) null,
    mdfr                 varchar(10) null
);

alter table tb_dsuse_rpt_dtl
    add primary key (dscdmng_id,uid,dscdmng_sn,usr_rpt_ln_id_no);

create table tb_prduct_info
(
    prduct_cd            varchar(14) not null,
    prdlst_mst_cd        varchar(14) null,
    prduct_nm            varchar(300) null,
    nrcd_se_nm           varchar(50) null,
    prtm_se_nm           varchar(200) null,
    prd_min_distb_qy     integer null,
    std_packng_stle_nm   varchar(200) null,
    prd_tot_pce_qy       integer null,
    pce_co_unit_nm       varchar(200) null,
    bssh_cd              varchar(10) null,
    rgs_dt               date null,
    upd_dt               date null,
    use_yn               varchar(1) not null,
    reg_dt               varchar(14) not null,
    rgtr                 varchar(10) not null,
    mdfcn_dt             varchar(14) null,
    mdfr                 varchar(10) null
);

alter table tb_prduct_info
    add primary key (prduct_cd);

create or replace table tb_jrdt_gov_info
(
    bssh_cd        varchar(10)  not null comment '마약류취급자식별_번호'
        primary key,
    of_cd          varchar(10)  null comment '기관_코드',
    of_nm          varchar(100) null comment '기관_명',
    up_of_nm       varchar(100) null comment '상위_기관_명',
    top_of_nm      varchar(100) null comment '최상위_기관_명',
    bass_adres     varchar(200) null comment '기본_주소',
    bass_dtl_adres varchar(200) null comment '상세_주소',
    use_yn         varchar(1)   not null comment '사용_여부',
    reg_dt         varchar(14)  not null comment '등록_일시',
    rgtr           varchar(10)  not null comment '등록자',
    mdfcn_dt       varchar(14)  null comment '수정_일시',
    mdfr           varchar(10)  null comment '수정자'
)
    comment '관할_관청_정보';

create or replace table tb_storge_info
(
    bssh_cd        varchar(10)  not null comment '마약류취급자식별_번호'
        primary key,
    bssh_nm        varchar(120) null comment '업체_명',
    use_at         char         null comment '사용_유무',
    storge_se_nm   varchar(200) null comment '저장소_유형',
    storge_no      varchar(16)  null comment '저장소_번호',
    storge_nm      varchar(100) null comment '저장소_명',
    bass_adres     varchar(200) null comment '기본_주소',
    bass_dtl_adres varchar(200) null comment '상세_주소',
    use_yn         varchar(1)   not null comment '사용_여부',
    reg_dt         varchar(14)  not null comment '등록_일시',
    rgtr           varchar(10)  not null comment '등록자',
    mdfcn_dt       varchar(14)  null comment '수정_일시',
    mdfr           varchar(10)  null comment '수정자'
)
    comment '저장소_정보';


/*
h2에서 에러 발생으로 comment 처리
alter table tb_dsuse_mgt_dtl
    add foreign key r_30 (dscdmng_id) references tb_dsuse_mgt (dscdmng_id);

alter table tb_dsuse_rpt
    add foreign key r_33 (dscdmng_id) references tb_dsuse_mgt (dscdmng_id);

alter table tb_dsuse_rpt_dtl
    add foreign key r_34 (dscdmng_id) references tb_dsuse_rpt (dscdmng_id);

alter table tb_bssh_info comment = '취급자_정보';
alter table tb_bssh_info change column bssh_cd bssh_cd varchar(10) not null comment '마약류취급자식별_번호';
alter table tb_bssh_info change column bssh_nm bssh_nm varchar(120) null comment '업체_명';
alter table tb_bssh_info change column induty_nm induty_nm varchar(200) null comment '업종_명';
alter table tb_bssh_info change column hdnt_cd hdnt_cd varchar(10) null comment '의료업자_구분';
alter table tb_bssh_info change column hdnt_nm hdnt_nm varchar(200) null comment '의료업자구분_명';
alter table tb_bssh_info change column bizrno bizrno varchar(10) null comment '사업자등록번호';
alter table tb_bssh_info change column rprsntv_nm rprsntv_nm varchar(60) null comment '대표자_명';
alter table tb_bssh_info change column chrg_nm chrg_nm varchar(60) null comment '담당자_명';
alter table tb_bssh_info change column hptl_no hptl_no varchar(20) null comment '요양기관_기호';
alter table tb_bssh_info change column join_yn join_yn varchar(200) null comment '회원가입_여부';
alter table tb_bssh_info change column bssh_stts_nm bssh_stts_nm varchar(200) null comment '상태';
alter table tb_bssh_info change column prmisn_no prmisn_no varchar(40) null comment '허가_번호';

alter table tb_dsuse_mgt comment = '폐기관리';
alter table tb_dsuse_mgt change column dscdmng_id dscdmng_id varchar(10) not null comment '폐기관리_id';
alter table tb_dsuse_mgt change column bssh_cd bssh_cd varchar(10) not null comment '마약류취급자식별_번호';
alter table tb_dsuse_mgt change column user_id user_id varchar(6) null comment '사용자_아이디';
alter table tb_dsuse_mgt change column prgrs_stts_cd prgrs_stts_cd varchar(10) null comment '진행_상태_코드';
alter table tb_dsuse_mgt change column rpt_rcept_no rpt_rcept_no varchar(30) null comment '보고_접수_번호';

alter table tb_dsuse_mgt_dtl comment = '폐기관리_상세';
alter table tb_dsuse_mgt_dtl change column dscdmng_id dscdmng_id varchar(10) not null comment '폐기관리_id';
alter table tb_dsuse_mgt_dtl change column dscdmng_sn dscdmng_sn varchar(3) not null comment '폐기관리_순번';
alter table tb_dsuse_mgt_dtl change column prdct_cd prdct_cd varchar(14) not null comment '제품_코드';
alter table tb_dsuse_mgt_dtl change column dsuse_qy dsuse_qy numeric not null comment '폐기_수량';
alter table tb_dsuse_mgt_dtl change column acdnt_ocrn_day acdnt_ocrn_day char(8) not null comment '사고_발생_일';
alter table tb_dsuse_mgt_dtl change column ocrn_rpt_day ocrn_rpt_day char(8) not null comment '발생_보고_일';
alter table tb_dsuse_mgt_dtl change column acdnt_ocrn_rsn acdnt_ocrn_rsn varchar(30) not null comment '사고_발생_사유';

alter table tb_dsuse_rpt comment = '폐기_보고';
alter table tb_dsuse_rpt change column dscdmng_id dscdmng_id varchar(10) not null comment '폐기관리_id';
alter table tb_dsuse_rpt change column uid uid varchar(128) null comment '보고자식별id';
alter table tb_dsuse_rpt change column rnd_rmk rnd_rmk varchar(4000) null comment '수불_비고';
alter table tb_dsuse_rpt change column hdr_de hdr_de varchar(8) null comment '취급_일자';
alter table tb_dsuse_rpt change column bssh_cd bssh_cd varchar(10) null comment '마약류취급자식별_번호';
alter table tb_dsuse_rpt change column rpt_se_cd rpt_se_cd varchar(3) null comment '보고_구분_코드';
alter table tb_dsuse_rpt change column usr_rpt_id_no usr_rpt_id_no varchar(30) null comment '사용자_보고_식별_번호';
alter table tb_dsuse_rpt change column ref_usr_rpt_id_no ref_usr_rpt_id_no varchar(30) null comment '참조_사용자_보고_식별_번호';
alter table tb_dsuse_rpt change column rpt_ty_cd rpt_ty_cd char(1) null comment '보고_유형_코드';
alter table tb_dsuse_rpt change column rmk rmk varchar(2000) null comment '비고';
alter table tb_dsuse_rpt change column rptr_nm rptr_nm varchar(60) null comment '보고자_명';
alter table tb_dsuse_rpt change column rptr_entrps_nm rptr_entrps_nm varchar(120) null comment '보고자_업체_명';
alter table tb_dsuse_rpt change column chrg_nm chrg_nm varchar(120) null comment '담당자_명';
alter table tb_dsuse_rpt change column chrg_tel_no chrg_tel_no varchar(60) null comment '담당자_전화_번호';
alter table tb_dsuse_rpt change column chrg_mp_no chrg_mp_no varchar(60) null comment '담당자_휴대폰_번호';
alter table tb_dsuse_rpt change column rnd_dtl_rpt_cnt rnd_dtl_rpt_cnt numeric null comment '수불_상세_보고_수';
alter table tb_dsuse_rpt change column dsuse_se_cd dsuse_se_cd varchar(10) null comment '폐기_구분_코드';
alter table tb_dsuse_rpt change column dsuse_prv_cd dsuse_prv_cd varchar(10) null comment '폐기_사유_코드';
alter table tb_dsuse_rpt change column dsuse_mth_cd dsuse_mth_cd varchar(10) null comment '폐기_방법_코드';
alter table tb_dsuse_rpt change column dsuse_loc dsuse_loc varchar(120) null comment '폐기_장소';
alter table tb_dsuse_rpt change column dsuse_de dsuse_de varchar(8) null comment '폐기_일자';
alter table tb_dsuse_rpt change column dsuse_instt_cd dsuse_instt_cd varchar(7) null comment '폐기_관할행정기관_코드';
alter table tb_dsuse_rpt change column atch_file_co atch_file_co numeric null comment '첨부_파일_건수';
alter table tb_dsuse_rpt change column register_id register_id varchar(20) null comment '등록자_id';
alter table tb_dsuse_rpt change column file_creat_dt file_creat_dt varchar(14) null comment '파일_생성_일시';

alter table tb_dsuse_rpt_dtl comment = '폐기_보고_상세';
alter table tb_dsuse_rpt_dtl change column dscdmng_id dscdmng_id varchar(10) not null comment '폐기관리_id';
alter table tb_dsuse_rpt_dtl change column dscdmng_sn dscdmng_sn varchar(3) not null comment '폐기관리_순번';
alter table tb_dsuse_rpt_dtl change column usr_rpt_ln_id_no usr_rpt_ln_id_no varchar(35) not null comment '사용자_보고_라인_식별_번호';
alter table tb_dsuse_rpt_dtl change column storge_no storge_no varchar(16) null comment '저장소_번호';
alter table tb_dsuse_rpt_dtl change column mvmn_ty_cd mvmn_ty_cd varchar(4) null comment '이동_유형_코드';
alter table tb_dsuse_rpt_dtl change column prduct_cd prduct_cd varchar(14) null comment '제품_코드';
alter table tb_dsuse_rpt_dtl change column mnf_no mnf_no varchar(20) null comment '제조_번호';
alter table tb_dsuse_rpt_dtl change column mnf_seq mnf_seq varchar(42) null comment '제조_일련번호';
alter table tb_dsuse_rpt_dtl change column min_distb_qy min_distb_qy numeric null comment '최소_유통단위_수량';
alter table tb_dsuse_rpt_dtl change column prd_min_distb_unit prd_min_distb_unit varchar(10) null comment '제품_최소_유통_단위';
alter table tb_dsuse_rpt_dtl change column pce_qy pce_qy numeric null comment '낱개단위_수량';
alter table tb_dsuse_rpt_dtl change column prd_pce_unit prd_pce_unit varchar(10) null comment '제품_낱개_단위';
alter table tb_dsuse_rpt_dtl change column prduct_nm prduct_nm varchar(300) null comment '제품_명';
alter table tb_dsuse_rpt_dtl change column prd_sgtin prd_sgtin varchar(68) null comment '제품_바코드';
alter table tb_dsuse_rpt_dtl change column prd_min_distb_qy prd_min_distb_qy numeric null comment '제품_최소_유통단위_수량';
alter table tb_dsuse_rpt_dtl change column prdct_tot_pce_qy prdct_tot_pce_qy numeric null comment '제품_총_낱개단위_수량';
alter table tb_dsuse_rpt_dtl change column prd_valid_de prd_valid_de varchar(8) null comment '제품_유효기한_일자';
alter table tb_dsuse_rpt_dtl change column file_creat_dt file_creat_dt varchar(14) null comment '파일_생성_일시';
alter table tb_dsuse_rpt_dtl change column dsuse_qy dsuse_qy numeric null comment '폐기_수량';

alter table tb_prduct_info comment = '품목_정보';
alter table tb_prduct_info change column prduct_cd prduct_cd varchar(14) not null comment '제품_코드';
alter table tb_prduct_info change column prdlst_mst_cd prdlst_mst_cd varchar(14) null comment '제품_대표_코드';
alter table tb_prduct_info change column prduct_nm prduct_nm varchar(300) null comment '품목_명';
alter table tb_prduct_info change column nrcd_se_nm nrcd_se_nm varchar(50) null comment '마약/항정_구분_명';
alter table tb_prduct_info change column prtm_se_nm prtm_se_nm varchar(200) null comment '중점/일반_구분';
alter table tb_prduct_info change column prd_min_distb_qy prd_min_distb_qy integer null comment '제품_최소_유통단위_수량';
alter table tb_prduct_info change column std_packng_stle_nm std_packng_stle_nm varchar(200) null comment '제품_최소_유통단위';
alter table tb_prduct_info change column prd_tot_pce_qy prd_tot_pce_qy integer null comment '제품_총_낱개단위_수량';
alter table tb_prduct_info change column pce_co_unit_nm pce_co_unit_nm varchar(200) null comment '제품_낱개_단위_명';
alter table tb_prduct_info change column bssh_cd bssh_cd varchar(10) null comment '마약류취급자식별_번호';
alter table tb_prduct_info change column rgs_dt rgs_dt date null comment '등록_일';
alter table tb_prduct_info change column upd_dt upd_dt date null comment '변경_일';

alter table tb_purchase_info comment = '구입대상_정보';
alter table tb_purchase_info change column bssh_cd bssh_cd varchar(10) not null comment '판매업체식별_번호';
alter table tb_purchase_info change column prduct_cd prduct_cd varchar(14) not null comment '제품_코드';
alter table tb_purchase_info change column rptr_entrps_nm rptr_entrps_nm varchar(120) null comment '판매_업체_명';
alter table tb_purchase_info change column prduct_nm prduct_nm varchar(300) null comment '제품_명';
alter table tb_purchase_info change column hdr_de hdr_de char(8) null comment '취급_일자';
alter table tb_purchase_info change column mnf_no mnf_no varchar(20) null comment '제조_번호';
alter table tb_purchase_info change column mnf_seq mnf_seq varchar(42) null comment '제조_일련번호';
alter table tb_purchase_info change column min_distb_qy min_distb_qy numeric null comment '최소_유통단위_수량';
alter table tb_purchase_info change column prd_min_distb_unit prd_min_distb_unit varchar(10) null comment '제품_최소_유통_단위';
alter table tb_purchase_info change column prd_min_distb_qy prd_min_distb_qy numeric null comment '제품_최소_유통단위_수량';
alter table tb_purchase_info change column pce_qy pce_qy numeric null comment '낱개단위_수량';
alter table tb_purchase_info change column prd_tot_pce_qy prd_tot_pce_qy numeric null comment '제품_총_낱개단위_수량';
alter table tb_purchase_info change column prd_pce_unit prd_pce_unit varchar(10) null comment '제품_낱개_단위';
alter table tb_purchase_info change column storge_no storge_no varchar(16) null comment '저장소_번호';
alter table tb_purchase_info change column storge_nm storge_nm varchar(100) null comment '저장소_명';
alter table tb_purchase_info change column bizrno bizrno varchar(10) null comment '사업자등록번호';
alter table tb_purchase_info change column prd_sgtin prd_sgtin varchar(68) null comment '제품_바코드';
alter table tb_purchase_info change column prd_valid_de prd_valid_de varchar(8) null comment '제품_유효기한_일자';
alter table tb_purchase_info change column pcm_rpt_at pcm_rpt_at varchar(1) null comment '구입보고존재여부';
alter table tb_purchase_info change column usr_rpt_id_no usr_rpt_id_no varchar(30) null comment '판매사용자_보고_식별_번호';
alter table tb_purchase_info change column chrg_nm chrg_nm varchar(120) null comment '판매담당자_명';
alter table tb_purchase_info change column chrg_tel_no chrg_tel_no varchar(60) null comment '판매담당자_전화_번호';
alter table tb_purchase_info change column ref_usr_rpt_id_no ref_usr_rpt_id_no varchar(22) null comment '참조_사용자_보고_식별_번호';
alter table tb_purchase_info change column prtm_se_nm prtm_se_nm varchar(200) null comment '중점/일반_구분';
alter table tb_purchase_info change column rpt_de rpt_de char(8) null comment '보고_일자';
alter table tb_purchase_info change column rpt_ty_nm rpt_ty_nm varchar(1) null comment '보고_유형_명';
alter table tb_purchase_info change column induty_cd_nm induty_cd_nm varchar(200) null comment '판매업체업종_명';

alter table tb_stock_info comment = '재고_정보';
alter table tb_stock_info change column storge_no storge_no varchar(16) not null comment '저장소_번호';
alter table tb_stock_info change column prduct_cd prduct_cd varchar(14) not null comment '제품_코드';
alter table tb_stock_info change column storge_nm storge_nm varchar(100) null comment '저장소_명';
alter table tb_stock_info change column prduct_nm prduct_nm varchar(300) null comment '제품_명';
alter table tb_stock_info change column mnf_no mnf_no varchar(20) null comment '제조_번호';
alter table tb_stock_info change column prd_valid_de prd_valid_de char(8) null comment '제품_유효_기한';
alter table tb_stock_info change column mnf_seq mnf_seq varchar(42) null comment '제조_일련번호';
alter table tb_stock_info change column min_distb_invt_qy min_distb_invt_qy numeric null comment '최소_유통_단위재고_수량';
alter table tb_stock_info change column prtm_se_cd prtm_se_cd varchar(200) null comment '중점/일반관리_구분_코드';
alter table tb_stock_info change column storge_se_nm storge_se_nm varchar(200) null comment '저장소_구분_명';
alter table tb_stock_info change column prd_min_distb_unit prd_min_distb_unit varchar(100) null comment '제품_최소_유통_단위';
alter table tb_stock_info change column prd_tot_pce_qy prd_tot_pce_qy integer null comment '제품_총_낱개단위_수량';
alter table tb_stock_info change column pce_invt_qy pce_invt_qy numeric null comment '낱개단위_재고_수량';
alter table tb_stock_info change column prd_pce_unit prd_pce_unit varchar(100) null comment '제품_낱개_단위';
alter table tb_stock_info change column ddln_ym ddln_ym char(8) null comment '재고기준_년월';
alter table tb_stock_info change column hdr_de hdr_de char(8) null comment '취급_일자';
*/

create table xit_user_info
(
    user_id            varchar(20)  null,
    orgnzt_id          varchar(20)  null,
    user_nm            varchar(50)  null,
    password           varchar(200) null,
    empl_no            varchar(20)  null,
    ihidnum            varchar(200) null,
    sexdstn_code       char         null,
    brthdy             varchar(20)  null,
    fxnum              varchar(20)  null,
    house_adres        varchar(150) null,
    password_hint      varchar(100) null,
    password_cnsr      varchar(100) null,
    house_end_telno    varchar(200) null,
    area_no            varchar(200) null,
    detail_adres       varchar(150) null,
    zip                varchar(6)   null,
    offm_telno         varchar(20)  null,
    mbtlnum            varchar(20)  null,
    email_adres        varchar(50)  null,
    ofcps_nm           varchar(60)  null,
    house_middle_telno varchar(200) null,
    group_id           varchar(20)  null,
    pstinst_code       varchar(8)   null,
    user_sttus_code    varchar(15)  null,
    esntl_id           varchar(20)  null,
    crtfc_dn_value     varchar(20)  null,
    sbscrb_de          datetime     null,
    LOGIN_FAIL         decimal(1)   null,
    login_fail_de      datetime     null
);

create table tb_cmm_api_log
(
    request_id    varchar(40)   not null comment '요청 ID'
        primary key,
    system_id     varchar(20)   null comment '시스템 ID(ENS|FIMS등)',
    req_system_id varchar(20)   null comment '요청시스템 ID(KAKAO|KT등)',
    method        varchar(10)   null comment '메소드(GET|PUT|POST|DELETE)',
    uri           varchar(255)  null comment '호출 URI',
    success       varchar(5)    null comment '성공/실패(true|false)',
    param         longtext      null comment '파라메터',
    response      longtext      null comment '호출 결과',
    message       longtext      null comment '메세지(에러메세지)',
    ip            varchar(30)   null comment 'IP',
    access_token  varchar(2000) null comment '토큰',
    session_id    varchar(255)  null comment '세션ID',
    updt_dt       datetime(3)   null comment '변경일시(now(3)-밀리세컨드까지)',
    updt_id       varchar(20)   null comment '변경자',
    regist_dt     datetime(3)   null comment '생성일시(now(3)-밀리세컨드까지)',
    regist_id     varchar(20)   null comment '생성자'
);
