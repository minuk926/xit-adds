/* -------------------------------------------------------------------------------- */
/* 공통                                                                             */
/* -------------------------------------------------------------------------------- */
create or replace table tb_action_grp
(
    grp_id varchar(50)  not null comment '그룹 id'
        primary key,
    grp_nm varchar(60)  not null comment '그룹 이름',
    dscrp  varchar(300) null comment '설명',
    reg_dt varchar(14)  not null comment '등록 일시'
)
    comment '기능 그룹';

create or replace table tb_auth_action
(
    auth_id varchar(50) not null comment '권한 id',
    grp_id  varchar(50) not null comment '그룹 id',
    reg_dt  varchar(14) null comment '등록 일시',
    primary key (auth_id, grp_id)
)
    comment '권한 기능';

create or replace table tb_auth_user
(
    auth_id varchar(50) not null comment '권한 id',
    user_id varchar(10) not null comment '사용자 id',
    reg_dt  varchar(14) null comment '등록 일시',
    primary key (auth_id, user_id)
)
    comment '권한 사용자';

create or replace table tb_authority
(
    auth_id      varchar(50)  not null comment '권한 id'
        primary key,
    auth_nm      varchar(60)  not null comment '권한 이름',
    dscrp        varchar(300) null comment '설명',
    inf_scp      varchar(20)  null comment '업무정보 범위',
    user_inf_scp varchar(20)  null comment '사용자정보 범위',
    reg_dt       varchar(14)  null comment '등록 일시'
)
    comment '권한';

create or replace table tb_cmn_code
(
    grp_id   varchar(6)   not null comment '그룹 id',
    code     varchar(15)  not null comment '코드',
    code_val varchar(100) null comment '코드 값',
    dscrp    varchar(300) null comment '설명',
    etc_1    varchar(300) null comment '기타 1',
    etc_2    varchar(300) null comment '기타 2',
    etc_3    varchar(300) null comment '기타 3',
    srt_ord  int          null comment '정렬 순서',
    use_yn   varchar(1)   not null comment '사용 여부',
    reg_dt   varchar(14)  null comment '등록 일시',
    rgtr     varchar(10)  null comment '등록자',
    mdfcn_dt varchar(14)  null comment '수정 일시',
    mdfr     varchar(10)  null comment '수정자',
    primary key (grp_id, code)
)
    comment '공통 코드';

create or replace table tb_code_ctgr
(
    ctgr_id  varchar(6)   not null comment '분류 id'
        primary key,
    ctgr_nm  varchar(100) null comment '분류 이름',
    dscrp    varchar(300) null comment '설명',
    use_yn   varchar(1)   not null comment '사용 여부',
    reg_dt   varchar(14)  null comment '등록 일시',
    rgtr     varchar(10)  null comment '등록자',
    mdfcn_dt varchar(14)  null comment '수정 일시',
    mdfr     varchar(10)  null comment '수정자'
)
    comment '공통 코드 분류';

create or replace table tb_code_grp
(
    grp_id   varchar(6)   not null comment '그룹 id'
        primary key,
    grp_nm   varchar(100) null comment '그룹 이름',
    dscrp    varchar(300) null comment '설명',
    ctgr_id  varchar(6)   null comment '분류 id',
    use_yn   varchar(1)   not null comment '사용 여부',
    reg_dt   varchar(14)  null comment '등록 일시',
    rgtr     varchar(10)  null comment '등록자',
    mdfcn_dt varchar(14)  null comment '수정 일시',
    mdfr     varchar(10)  null comment '수정자'
)
    comment '공통 코드 그룹';

create or replace table tb_dept
(
    dept_cd            varchar(7)   not null comment '부서 코드'
        primary key,
    dept_nm            varchar(100) null comment '부서 명',
    dept_telno         varchar(20)  null comment '부서 전화번호',
    dept_fxno          varchar(20)  null comment '부서 팩스번호',
    inst_cd            varchar(7)   null comment '기관 코드',
    sgg_cd             varchar(5)   null comment '시군구 코드',
    pstofc_nm          varchar(60)  null comment '우체국 명',
    vrbacnt_link_se_cd varchar(2)   null comment '가상계좌 연계 구분 코드',
    rg_no_header       varchar(10)  null comment '등기 번호 헤더',
    sndng_link_se_cd   varchar(2)   null comment '발송 연계 구분 코드',
    egp_rcept_id       varchar(5)   null comment 'e그린 접수우체국국기호',
    egp_apvl_nb        varchar(10)  null comment 'e그린 후납계약승인번호',
    egp_con_org        varchar(4)   null comment 'e그린 외부기관구분코드',
    egp_post_inst_id   varchar(20)  null comment 'e그린 우정정보센터 기관id',
    dtbn_bank_nm       varchar(30)  null comment '교부 은행 명',
    dtbn_actno         varchar(20)  null comment '교부 계좌번호',
    srvr_os            varchar(20)  null comment '서버 os',
    doc_header         varchar(60)  null comment '문서 머릿말',
    cvlcpt_link_se_cd  varchar(2)   null comment '민원 연계 구분 코드',
    nxrp_link_yn       varchar(1)   not null comment '세외수입 연계 여부',
    nxrp_rgn_se_cd     varchar(1)   null comment '세외수입 지역 구분 코드',
    nxrp_link_srvc_url varchar(200) null comment '세외수입 연계 서비스 url',
    use_yn             varchar(1)   not null comment '사용 여부',
    reg_dt             varchar(14)  null comment '등록 일시',
    rgtr               varchar(10)  null comment '등록자',
    mdfcn_dt           varchar(14)  null comment '수정 일시',
    mdfr               varchar(10)  null comment '수정자'
)
    comment '부서';

create or replace table tb_file
(
    file_id   varchar(13)  not null comment '파일 id'
        primary key,
    inf_type  varchar(3)   not null comment '관련정보 유형',
    inf_key   varchar(64)  null comment '관련정보 key',
    sub_type  varchar(3)   null comment '관련정보 하위 분류',
    file_nm   varchar(128) not null comment '파일 이름',
    file_path varchar(256) not null comment '파일 경로',
    mime_type varchar(64)  null comment '파일 유형',
    file_size int          not null comment '파일 크기',
    dnld_cnt  int          not null comment '다운로드 횟수',
    srt_ord   int          not null comment '정렬 순서',
    use_yn    varchar(1)   not null comment '사용 여부',
    reg_dt    varchar(14)  not null comment '등록 일시',
    rgtr      varchar(10)  not null comment '등록자'
)
    comment '관련 파일';

create or replace table tb_grp_action
(
    grp_id varchar(50) not null comment '그룹 id',
    action varchar(60) not null comment '기능',
    reg_dt varchar(14) not null comment '등록 일시',
    rgtr   varchar(10) not null comment '등록자',
    primary key (grp_id, action)
)
    comment '그룹별 기능';

create or replace table tb_login_policy
(
    user_id     varchar(10) not null comment '사용자 id'
        primary key,
    ip_addr     varchar(30) null comment 'ip 주소',
    dplct_yn    varchar(1)  not null comment '중복접근 여부',
    limit_yn    varchar(1)  not null comment '접근제한 여부',
    sub_ip_addr varchar(30) null comment '보조 ip 주소',
    reg_dt      varchar(14) null comment '등록 일시',
    rgtr        varchar(10) null comment '등록자',
    mdfcn_dt    varchar(14) null comment '수정 일시',
    mdfr        varchar(10) null comment '수정자'
)
    comment '로그인 정책';

create or replace table tb_menu
(
    menu_no      int          not null comment '메뉴 id'
        primary key,
    menu_nm      varchar(60)  not null comment '메뉴 이름',
    prnt_no      int          null comment '상위 메뉴 id',
    pgrm_file_nm varchar(60)  null comment '프로그램 파일',
    action       varchar(300) null comment '실행기능',
    dscrp        varchar(300) null comment '설명',
    img_nm       varchar(60)  null comment '이미지 이름',
    img_cnf      varchar(100) null comment '이미지 설정',
    srt_ord      int          null comment '정렬 순서',
    reg_dt       varchar(14)  not null comment '등록 일시',
    rgtr         varchar(10)  not null comment '등록자'
)
    comment '메뉴';

create or replace table tb_sgg
(
    sgg_cd           varchar(5)   not null comment '시군구 코드'
        primary key,
    inst_cd          varchar(7)   not null comment '기관 코드',
    inst_nm          varchar(100) not null comment '기관 명',
    inst_addr        varchar(200) null comment '기관 주소',
    inst_daddr       varchar(200) null comment '기관 상세주소',
    inst_zip         varchar(6)   null comment '기관 우편번호',
    offcs_file_path  varchar(200) null comment '직인 파일 경로',
    offcs_file_nm    varchar(100) null comment '직인 파일 명',
    sgg_nm           varchar(60)  not null comment '시군구 명',
    up_inst_cd       varchar(7)   null comment '상위 기관 코드',
    up_inst_nm       varchar(100) null comment '상위 기관 명',
    inst_se_cd       varchar(2)   null comment '기관 구분 코드',
    logo_file_nm     varchar(100) null comment '로고 파일 명',
    symbol_file_nm   varchar(100) null comment '상징 파일 명',
    symbol_file_path varchar(200) null comment '상징 파일 경로',
    logo_file_path   varchar(200) null comment '로고 파일 경로',
    use_yn           varchar(1)   not null comment '사용 여부',
    reg_dt           varchar(14)  null comment '등록 일시',
    rgtr             varchar(10)  null comment '등록자',
    mdfcn_dt         varchar(14)  null comment '수정 일시',
    mdfr             varchar(10)  null comment '수정자'
)
    comment '시군구';

create or replace table tb_sys_log
(
    log_id    varchar(24)   not null comment '로그 id'
        primary key,
    log_type  varchar(16)   null comment '로그 유형',
    url       varchar(256)  null comment 'url',
    cls_nm    varchar(128)  null comment '클래스 이름',
    mtd_nm    varchar(128)  null comment '메소드 이름',
    data_cnt  int           null comment '데이터 수',
    data_nm   varchar(1000) null comment '데이터 이름',
    psnl_info varchar(256)  null comment '개인 정보',
    ip_addr   varchar(32)   null comment 'ip 주소',
    file_nm   varchar(128)  null comment '파일 이름',
    reg_dt    varchar(14)   null comment '등록 일시',
    user_id   varchar(10)   null comment '사용자 id'
)
    comment '시스템 로그';

create or replace table tb_user
(
    user_id     varchar(10)  not null comment '사용자 id'
        primary key,
    user_acnt   varchar(20)  not null comment '사용자 계정',
    user_nm     varchar(50)  not null comment '사용자 이름',
    passwd      varchar(200) not null comment '비밀번호',
    passwd_hint varchar(100) null comment '비밀번호 힌트',
    passwd_nsr  varchar(100) null comment '비밀번호 힌트 답',
    emp_no      varchar(20)  null comment '사원 번호',
    gender      varchar(1)   null comment '성별',
    zip         varchar(6)   null comment '우편번호',
    addr        varchar(150) null comment '주소',
    daddr       varchar(150) null comment '상세주소',
    area_no     varchar(10)  null comment '지역 번호',
    eml_adrs    varchar(50)  null comment '이메일 주소',
    org_id      varchar(20)  null comment '조직 id',
    grp_id      varchar(20)  null comment '그룹 id',
    nstt_cd     varchar(8)   not null comment '소속기관 코드',
    pos_nm      varchar(60)  null comment '직위 이름',
    crtfc_dn    varchar(20)  null comment '인증 dn값',
    lock_yn     varchar(1)   null comment '잠김 여부',
    lock_cnt    int          not null comment '잠김 횟수',
    lock_dt     varchar(14)  null comment '잠김 일시',
    stts        varchar(3)   not null comment '상태',
    fxno        varchar(20)  null comment '팩스번호',
    telno       varchar(20)  null comment '전화번호',
    mbl_telno   varchar(20)  null comment '휴대 전화번호',
    brdt        varchar(20)  null comment '생년월일',
    dept_cd     varchar(7)   null comment '부서 코드',
    rsdnt_no    varchar(200) null comment '주민등록 번호',
    use_yn      varchar(1)   not null comment '사용 여부',
    reg_dt      varchar(14)  null comment '등록 일시',
    rgtr        varchar(10)  null comment '등록자',
    mdfcn_dt    varchar(14)  null comment '수정 일시',
    mdfr        varchar(10)  null comment '수정자'
)
    comment '사용자';
/* -------------------------------------------------------------------------------- */
/* 공통                                                                             */
/* -------------------------------------------------------------------------------- */




/* -------------------------------------------------------------------------------- */
/* adds                                                                             */
/* -------------------------------------------------------------------------------- */

create or replace table tb_bssh_info
(
    bssh_cd      varchar(10)  not null comment '마약류취급자식별_번호'
        primary key,
    bssh_nm      varchar(120) null comment '업체_명',
    induty_nm    varchar(200) null comment '업종_명',
    hdnt_cd      varchar(10)  null comment '의료업자_구분',
    hdnt_nm      varchar(200) null comment '의료업자구분_명',
    bizrno       varchar(10)  null comment '사업자등록번호',
    rprsntv_nm   varchar(60)  null comment '대표자_명',
    chrg_nm      varchar(60)  null comment '담당자_명',
    hptl_no      varchar(20)  null comment '요양기관_기호',
    join_yn      varchar(200) null comment '회원가입_여부',
    bssh_stts_nm varchar(200) null comment '상태',
    prmisn_no    varchar(40)  null comment '허가_번호',
    use_yn       varchar(1)   not null comment '사용_여부',
    reg_dt       varchar(14)  not null comment '등록_일시',
    rgtr         varchar(10)  not null comment '등록자',
    mdfcn_dt     varchar(14)  null comment '수정_일시',
    mdfr         varchar(10)  null comment '수정자'
)
    comment '취급자_정보';

create or replace table tb_dsuse_mgt
(
    dscdmng_id    varchar(10) not null comment '폐기관리_id'
        primary key,
    user_id       varchar(10) null comment '사용자_아이디',
    bssh_cd       varchar(10) not null comment '마약류취급자식별_번호',
    prgrs_stts_cd varchar(10) null comment '진행_상태_코드',
    rpt_rcept_no  varchar(30) null comment '보고_접수_번호',
    use_yn        varchar(1)  not null comment '사용_여부',
    reg_dt        varchar(14) not null comment '등록_일시',
    rgtr          varchar(10) not null comment '등록자',
    mdfcn_dt      varchar(14) null comment '수정_일시',
    mdfr          varchar(10) null comment '수정자'
)
    comment '폐기관리';

create or replace table tb_dsuse_mgt_dtl
(
    dscdmng_id     varchar(10) not null comment '폐기관리_id',
    dscdmng_sn     varchar(3)  not null comment '폐기관리_순번',
    prdct_cd       varchar(14) not null comment '제품_코드',
    dsuse_qy       decimal     not null comment '폐기_수량',
    acdnt_ocrn_day char(8)     not null comment '사고_발생_일',
    ocrn_rpt_day   char(8)     not null comment '발생_보고_일',
    acdnt_ocrn_rsn varchar(30) not null comment '사고_발생_사유',
    use_yn         varchar(1)  not null comment '사용_여부',
    reg_dt         varchar(14) not null comment '등록_일시',
    rgtr           varchar(10) not null comment '등록자',
    mdfcn_dt       varchar(14) null comment '수정_일시',
    mdfr           varchar(10) null comment '수정자',
    primary key (dscdmng_id, dscdmng_sn),
    constraint fk_dsuse_mgt_dtl_dsuse_mgt
        foreign key (dscdmng_id) references tb_dsuse_mgt (dscdmng_id)
)
    comment '폐기관리_상세';

create or replace table tb_dsuse_rpt
(
    dscdmng_id        varchar(10)   not null comment '폐기관리_id',
    uid               varchar(128)  not null comment '보고자식별id',
    rnd_rmk           varchar(4000) null comment '수불_비고',
    hdr_de            varchar(8)    null comment '취급_일자',
    bssh_cd           varchar(10)   null comment '마약류취급자식별_번호',
    rpt_se_cd         varchar(3)    null comment '보고_구분_코드',
    usr_rpt_id_no     varchar(30)   null comment '사용자_보고_식별_번호',
    ref_usr_rpt_id_no varchar(30)   null comment '참조_사용자_보고_식별_번호',
    rpt_ty_cd         char          null comment '보고_유형_코드',
    rmk               varchar(2000) null comment '비고',
    rptr_nm           varchar(60)   null comment '보고자_명',
    rptr_entrps_nm    varchar(120)  null comment '보고자_업체_명',
    chrg_nm           varchar(120)  null comment '담당자_명',
    chrg_tel_no       varchar(60)   null comment '담당자_전화_번호',
    chrg_mp_no        varchar(60)   null comment '담당자_휴대폰_번호',
    rnd_dtl_rpt_cnt   decimal       null comment '수불_상세_보고_수',
    dsuse_se_cd       varchar(10)   null comment '폐기_구분_코드',
    dsuse_prv_cd      varchar(10)   null comment '폐기_사유_코드',
    dsuse_mth_cd      varchar(10)   null comment '폐기_방법_코드',
    dsuse_loc         varchar(120)  null comment '폐기_장소',
    dsuse_de          varchar(8)    null comment '폐기_일자',
    dsuse_instt_cd    varchar(7)    null comment '폐기_관할행정기관_코드',
    atch_file_co      decimal       null comment '첨부_파일_건수',
    register_id       varchar(20)   null comment '등록자_id',
    file_creat_dt     varchar(14)   null comment '파일_생성_일시',
    use_yn            varchar(1)    not null comment '사용_여부',
    reg_dt            varchar(14)   not null comment '등록_일시',
    rgtr              varchar(10)   not null comment '등록자',
    mdfcn_dt          varchar(14)   null comment '수정_일시',
    mdfr              varchar(10)   null comment '수정자',
    primary key (dscdmng_id, uid),
    constraint fk_dsuse_rpt_dsuse_mgt
        foreign key (dscdmng_id) references tb_dsuse_mgt (dscdmng_id)
)
    comment '폐기_보고';

create or replace table tb_dsuse_rpt_dtl
(
    dscdmng_id         varchar(10)  not null comment '폐기관리_id',
    uid                varchar(128) not null comment '보고자식별id',
    dscdmng_sn         varchar(3)   not null comment '폐기관리_순번',
    usr_rpt_ln_id_no   varchar(35)  not null comment '사용자_보고_라인_식별_번호',
    storge_no          varchar(16)  null comment '저장소_번호',
    mvmn_ty_cd         varchar(4)   null comment '이동_유형_코드',
    prduct_cd          varchar(14)  null comment '제품_코드',
    mnf_no             varchar(20)  null comment '제조_번호',
    mnf_seq            varchar(42)  null comment '제조_일련번호',
    min_distb_qy       decimal      null comment '최소_유통단위_수량',
    prd_min_distb_unit varchar(10)  null comment '제품_최소_유통_단위',
    pce_qy             decimal      null comment '낱개단위_수량',
    prd_pce_unit       varchar(10)  null comment '제품_낱개_단위',
    prduct_nm          varchar(300) null comment '제품_명',
    prd_sgtin          varchar(68)  null comment '제품_바코드',
    prd_min_distb_qy   decimal      null comment '제품_최소_유통단위_수량',
    prdct_tot_pce_qy   decimal      null comment '제품_총_낱개단위_수량',
    prd_valid_de       varchar(8)   null comment '제품_유효기한_일자',
    file_creat_dt      varchar(14)  null comment '파일_생성_일시',
    dsuse_qy           decimal      null comment '폐기_수량',
    use_yn             varchar(1)   not null comment '사용_여부',
    reg_dt             varchar(14)  not null comment '등록_일시',
    rgtr               varchar(10)  not null comment '등록자',
    mdfcn_dt           varchar(14)  null comment '수정_일시',
    mdfr               varchar(10)  null comment '수정자',
    primary key (dscdmng_id, uid, dscdmng_sn, usr_rpt_ln_id_no),
    constraint fk_dsuse_rpt_dtl_dsuse_rpt
        foreign key (dscdmng_id) references tb_dsuse_rpt (dscdmng_id)
)
    comment '폐기_보고_상세';

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

create or replace table tb_prduct_info
(
    prduct_cd          varchar(14)  not null comment '제품_코드'
        primary key,
    prdlst_mst_cd      varchar(14)  null comment '제품_대표_코드',
    prduct_nm          varchar(300) null comment '품목_명',
    nrcd_se_nm         varchar(50)  null comment '마약/항정_구분_명',
    prtm_se_nm         varchar(200) null comment '중점/일반_구분',
    prd_min_distb_qy   int          null comment '제품_최소_유통단위_수량',
    std_packng_stle_nm varchar(200) null comment '제품_최소_유통단위',
    prd_tot_pce_qy     int          null comment '제품_총_낱개단위_수량',
    pce_co_unit_nm     varchar(200) null comment '제품_낱개_단위_명',
    bssh_cd            varchar(10)  null comment '마약류취급자식별_번호',
    rgs_dt             date         null comment '등록_일',
    upd_dt             date         null comment '변경_일',
    use_yn             varchar(1)   not null comment '사용_여부',
    reg_dt             varchar(14)  not null comment '등록_일시',
    rgtr               varchar(10)  not null comment '등록자',
    mdfcn_dt           varchar(14)  null comment '수정_일시',
    mdfr               varchar(10)  null comment '수정자'
)
    comment '품목_정보';

create or replace table tb_purchase_info
(
    rptr_entrps_nm     varchar(120) null comment '판매_업체_명',
    prduct_nm          varchar(300) null comment '제품_명',
    hdr_de             char(8)      null comment '취급_일자',
    mnf_no             varchar(20)  null comment '제조_번호',
    mnf_seq            varchar(42)  null comment '제조_일련번호',
    min_distb_qy       decimal      null comment '최소_유통단위_수량',
    prd_min_distb_unit varchar(10)  null comment '제품_최소_유통_단위',
    prd_min_distb_qy   decimal      null comment '제품_최소_유통단위_수량',
    pce_qy             decimal      null comment '낱개단위_수량',
    prd_tot_pce_qy     decimal      null comment '제품_총_낱개단위_수량',
    prd_pce_unit       varchar(10)  null comment '제품_낱개_단위',
    storge_no          varchar(16)  null comment '저장소_번호',
    storge_nm          varchar(100) null comment '저장소_명',
    bizrno             varchar(10)  null comment '사업자등록번호',
    prd_sgtin          varchar(68)  null comment '제품_바코드',
    prd_valid_de       varchar(8)   null comment '제품_유효기한_일자',
    pcm_rpt_at         varchar(1)   null comment '구입보고존재여부',
    usr_rpt_id_no      varchar(30)  null comment '판매사용자_보고_식별_번호',
    chrg_nm            varchar(120) null comment '판매담당자_명',
    chrg_tel_no        varchar(60)  null comment '판매담당자_전화_번호',
    ref_usr_rpt_id_no  varchar(22)  null comment '참조_사용자_보고_식별_번호',
    prtm_se_nm         varchar(200) null comment '중점/일반_구분',
    rpt_de             char(8)      null comment '보고_일자',
    rpt_ty_nm          varchar(1)   null comment '보고_유형_명',
    induty_cd_nm       varchar(200) null comment '판매업체업종_명',
    bssh_cd            varchar(10)  not null comment '판매업체식별_번호',
    prduct_cd          varchar(14)  not null comment '제품_코드',
    use_yn             varchar(1)   not null,
    reg_dt             varchar(14)  not null,
    rgtr               varchar(10)  not null,
    mdfcn_dt           varchar(14)  null,
    mdfr               varchar(10)  null,
    primary key (bssh_cd, prduct_cd)
)
    comment '구입대상_정보';

create or replace table tb_stock_info
(
    storge_no          varchar(16)  not null comment '저장소_번호',
    prduct_cd          varchar(14)  not null comment '제품_코드',
    storge_nm          varchar(100) null comment '저장소_명',
    prduct_nm          varchar(300) null comment '제품_명',
    mnf_no             varchar(20)  null comment '제조_번호',
    prd_valid_de       char(8)      null comment '제품_유효_기한',
    mnf_seq            varchar(42)  null comment '제조_일련번호',
    prd_min_distb_unit varchar(100) null comment '제품_최소_유통_단위',
    prd_tot_pce_qy     int          null comment '제품_총_낱개단위_수량',
    pce_invt_qy        decimal      null comment '낱개단위_재고_수량',
    prd_pce_unit       varchar(100) null comment '제품_낱개_단위',
    prtm_se_cd         varchar(200) null comment '중점/일반관리_구분_코드',
    storge_se_nm       varchar(200) null comment '저장소_구분_명',
    min_distb_invt_qy  decimal      null comment '최소_유통_단위재고_수량',
    hdr_de             char(8)      null comment '취급_일자',
    ddln_ym            char(8)      null comment '재고기준_년월',
    use_yn             varchar(1)   not null,
    reg_dt             varchar(14)  not null,
    rgtr               varchar(10)  not null,
    mdfcn_dt           varchar(14)  null,
    mdfr               varchar(10)  null,
    primary key (storge_no, prduct_cd)
)
    comment '재고_정보';

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


create or replace table tb_cmm_api_log
(
    request_id    varchar(40)   not null comment '요청 id'
        primary key,
    system_id     varchar(20)   null comment '시스템 id(ens|fims등)',
    req_system_id varchar(20)   null comment '요청시스템 id(kakao|kt등)',
    method        varchar(10)   null comment '메소드(get|put|post|delete)',
    uri           varchar(255)  null comment '호출 uri',
    success       varchar(5)    null comment '성공/실패(true|false)',
    param         longtext      null comment '파라메터',
    response      longtext      null comment '호출 결과',
    message       longtext      null comment '메세지(에러메세지)',
    ip            varchar(30)   null comment 'ip',
    access_token  varchar(2000) null comment '토큰',
    session_id    varchar(255)  null comment '세션id',
    updt_dt       datetime(3)   null comment '변경일시(now(3)-밀리세컨드까지)',
    updt_id       varchar(20)   null comment '변경자',
    regist_dt     datetime(3)   null comment '생성일시(now(3)-밀리세컨드까지)',
    regist_id     varchar(20)   null comment '생성자'
)
    comment 'api 호출 로그';

create or replace table tb_cmm_refresh_token
(
    id            varchar(20)  not null comment 'id'
        primary key,
    refresh_token varchar(256) not null comment 'refreshtoken',
    regist_dt     timestamp    null comment '등록일시',
    updt_dt       timestamp    null comment '변경일시'
)
    comment '리프레쉬토큰';
/* -------------------------------------------------------------------------------- */
/* adds                                                                             */
/* -------------------------------------------------------------------------------- */

/*
create table xit_user_info
(
    user_id            varchar(20)          not null comment '사용자 id'
        primary key,
    orgnzt_id          varchar(20)          null comment '조직 id',
    user_nm            varchar(50)          null comment '사용자 명',
    password           varchar(200)         null comment '비밀번호',
    empl_no            varchar(20)          null comment '사원 번호',
    ihidnum            varchar(200)         null comment '주민등록번호',
    sexdstn_code       char                 null comment '성별 코드',
    brthdy             varchar(20)          null comment '생일',
    fxnum              varchar(20)          null comment '팩스번호',
    house_adres        varchar(150)         null comment '주택 주소',
    password_hint      varchar(100)         null comment '비밀번호 힌트',
    password_cnsr      varchar(100)         null comment '비밀번호 정답',
    house_end_telno    varchar(200)         null comment '주택 끝 전화번호',
    area_no            varchar(200)         null comment '지역 번호',
    detail_adres       varchar(150)         null comment '상세 주소',
    zip                varchar(6)           null comment '우편번호',
    offm_telno         varchar(20)          null comment '사무실 전화번호',
    mbtlnum            varchar(20)          null comment '이동전화번호',
    email_adres        varchar(50)          null comment '이메일 주소',
    ofcps_nm           varchar(60)          null comment '직위 명',
    house_middle_telno varchar(200)         null comment '주택 중간 전화번호',
    group_id           varchar(20)          null comment '그룹 id',
    pstinst_code       varchar(8)           null comment '소속기관 코드',
    user_sttus_code    varchar(15)          null comment '사용자 상태 코드',
    esntl_id           varchar(20)          null comment '고유 id',
    crtfc_dn_value     varchar(20)          null comment '인증 dn 값',
    sbscrb_de          datetime             null comment '가입 일',
    login_fail         decimal(1) default 0 null,
    login_fail_de      datetime             null
)
    comment '사용자 정보';
 */
