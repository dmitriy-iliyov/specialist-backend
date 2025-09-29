create index idx_users_email on users (email);

create index idx_contacts_owner_id on contacts (owner_id);
create index idx_contacts_contact on contacts (contact);

create index idx_appointments_date_status on appointments (date, status);
create index idx_appointments_vid_date on appointments (volunteer_id, date);
create index idx_appointments_vid_date_status on appointments (volunteer_id, date, status);
create index idx_appointments_cid_date_status on appointments (customer_id, date, status);
create index idx_appointments_vid_cid on appointments (volunteer_id, customer_id);
create index idx_appointments_status_cid on appointments (status, customer_id);
create index idx_appointments_status_vid on appointments (status, volunteer_id);

create index idx_intervals_oid_date on work_intervals (owner_id, date);
create index idx_intervals_oid_date_start_t on work_intervals (owner_id, date, start_t);

CREATE INDEX idx_customers_lower_first_name ON customers (LOWER(first_name));
CREATE INDEX idx_customers_lower_second_name ON customers (LOWER(second_name));
CREATE INDEX idx_customers_lower_last_name ON customers (LOWER(last_name));

create index idx_doctors_approved_profile_status on doctors (is_approved, profile_status_id);
create index idx_doctors_gender_approved_profile_status on doctors (gender, is_approved, profile_status_id);
create index idx_doctor_specialization_relations_doctor_id on doctor_specialization_relations (doctor_id);
create index idx_doctor_specialization_relations_specialization_id on doctor_specialization_relations (specialization_id);
CREATE INDEX idx_doctors_lower_first_name ON doctors (LOWER(first_name));
CREATE INDEX idx_doctors_lower_second_name ON doctors (LOWER(second_name));
CREATE INDEX idx_doctors_lower_last_name ON doctors (LOWER(last_name));

create index idx_jurists_approved_profile_status on jurists (is_approved, profile_status_id);
create index idx_jurists_gender_approved_profile_status on jurists (gender, is_approved, profile_status_id);
create index idx_jurists_type_id_approved_profile_status on jurists (type_id, is_approved, profile_status_id);
create index idx_jurist_specialization_relations_doctor_id on jurist_specialization_relations (jurist_id);
create index idx_jurist_specialization_relations_specialization_id on jurist_specialization_relations (specialization_id);
CREATE INDEX idx_jurists_lower_first_name ON jurists (LOWER(first_name));
CREATE INDEX idx_jurists_lower_second_name ON jurists (LOWER(second_name));
CREATE INDEX idx_jurists_lower_last_name ON jurists (LOWER(last_name));