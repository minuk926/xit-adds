create table tb_action_grp
(
    grp_id varchar(50)  not null comment '그룹 id'
        primary key,
    grp_nm varchar(60)  not null comment '그룹 이름',
    dscrp  varchar(300) null comment '설명',
    reg_dt varchar(14)  not null comment '등록 일시'
)
    comment '기능 그룹';

create table tb_auth_action
(
    auth_id varchar(50) not null comment '권한 id',
    grp_id  varchar(50) not null comment '그룹 id',
    reg_dt  varchar(14) null comment '등록 일시',
    primary key (auth_id, grp_id)
)
    comment '권한 기능';

create table tb_auth_user
(
    auth_id varchar(50) not null comment '권한 id',
    user_id varchar(10) not null comment '사용자 id',
    reg_dt  varchar(14) null comment '등록 일시',
    primary key (auth_id, user_id)
)
    comment '권한 사용자';

create table tb_authority
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

create table tb_cmn_code
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

create table tb_code_ctgr
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

create table tb_code_grp
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

create table tb_dept
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

create table tb_file
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

create table tb_grp_action
(
    grp_id varchar(50) not null comment '그룹 id',
    action varchar(60) not null comment '기능',
    reg_dt varchar(14) not null comment '등록 일시',
    rgtr   varchar(10) not null comment '등록자',
    primary key (grp_id, action)
)
    comment '그룹별 기능';

create table tb_login_policy
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

create table tb_menu
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

create table tb_sgg
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

create table tb_sys_log
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

create table tb_user
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
