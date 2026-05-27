create schema if not exists hospital;

set default_table_access_method = heap;

create table if not exists hospital.file_entity
(
    id            varchar primary key,
    original_name varchar,
    file_name     varchar,
    size          bigint,
    content_type  varchar,
    path          varchar
);

create table hospital.wards
(
    id         varchar primary key,
    created_at timestamp(6) with time zone not null,
    updated_at timestamp(6) with time zone not null,
    created_by varchar                     not null,
    updated_by varchar                     not null,
    deleted    boolean default false       not null,
    type       varchar,
    capacity   integer default 0,
    occupied   integer default 0
);

create table hospital.permissions
(
    id   varchar primary key,
    name varchar,
    code varchar not null unique
);

create table hospital.roles
(
    id   varchar primary key,
    name varchar,
    code varchar not null unique
);

create table hospital.role_permissions
(
    role_id       varchar not null,
    permission_id varchar not null,
    primary key (role_id, permission_id),

    constraint fk_role_permissions_role foreign key (role_id) references hospital.roles (id) on delete cascade,
    constraint fk_role_permissions_permission foreign key (permission_id) references hospital.permissions (id) on delete cascade
);

create table hospital.users
(
    id               varchar primary key,
    created_at       timestamp(6) with time zone not null,
    updated_at       timestamp(6) with time zone not null,
    created_by       varchar                     not null,
    updated_by       varchar                     not null,
    deleted          boolean default false       not null,
    super_admin      boolean,
    password         varchar                     not null,
    last_name        varchar                     not null,
    first_name       varchar                     not null,
    username         varchar                     not null unique,
    profile_image_id varchar,
    role_id          varchar,

    constraint fk_users_role foreign key (role_id) references hospital.roles (id) on delete set null,
    constraint fk_users_profile_image foreign key (profile_image_id) references hospital.file_entity (id) on delete set null
);

create table hospital.doctors
(
    id             varchar primary key,
    specialization varchar,
    room_number    varchar not null unique,

    constraint fk_doctors_users foreign key (id) references hospital.users (id) on delete cascade
);

create table hospital.patients
(
    id             varchar primary key,
    created_at     timestamp(6) with time zone not null,
    updated_at     timestamp(6) with time zone not null,
    created_by     varchar                     not null,
    updated_by     varchar                     not null,
    deleted        boolean default false       not null,
    first_name     varchar,
    last_name      varchar,
    birth_date     date,
    phone          varchar(20) unique,
    blood_group    varchar,
    patient_status varchar,
    ward_id        varchar,

    constraint fk_patients_ward foreign key (ward_id) references hospital.wards (id) on delete set null
);

create table hospital.booking
(
    id           varchar primary key,
    created_at   timestamp(6) with time zone not null,
    updated_at   timestamp(6) with time zone not null,
    created_by   varchar                     not null,
    updated_by   varchar                     not null,
    deleted      boolean default false       not null,
    date_time    timestamp(6) with time zone,
    queue_number integer                     not null,
    doctor_id    varchar,
    patient_id   varchar,
    status       varchar,

    constraint fk_booking_doctor foreign key (doctor_id) references hospital.doctors (id) on delete set null,
    constraint fk_booking_patient foreign key (patient_id) references hospital.patients (id) on delete set null
);

create table hospital.medical_records
(
    id             varchar primary key,
    created_at     timestamp(6) with time zone not null,
    updated_at     timestamp(6) with time zone not null,
    created_by     varchar                     not null,
    updated_by     varchar                     not null,
    deleted        boolean default false       not null,
    diagnosis      text                        not null,
    treatment_plan varchar,
    booking_id     varchar,
    patient_id     varchar,
    duration       varchar                     not null,

    constraint fk_records_booking foreign key (booking_id) references hospital.booking (id) on delete set null,
    constraint fk_records_patient foreign key (patient_id) references hospital.patients (id) on delete set null
);