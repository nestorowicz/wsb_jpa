DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS doctor CASCADE;
DROP TABLE IF EXISTS patient CASCADE;
DROP TABLE IF EXISTS visit CASCADE;
DROP TABLE IF EXISTS medical_treatment CASCADE;


CREATE TABLE IF NOT EXISTS address
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    city          VARCHAR(255),
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    postal_code   VARCHAR(255),
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS doctor
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    first_name       VARCHAR(255)                            NOT NULL,
    last_name        VARCHAR(255)                            NOT NULL,
    telephone_number VARCHAR(255)                            NOT NULL,
    email            VARCHAR(255),
    doctor_number    VARCHAR(255)                            NOT NULL,
    specialization   VARCHAR(255)                            NOT NULL,
    address_id       BIGINT                                  NOT NULL,
    CONSTRAINT pk_doctor PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS patient
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    first_name       VARCHAR(255)                            NOT NULL,
    last_name        VARCHAR(255)                            NOT NULL,
    telephone_number VARCHAR(255)                            NOT NULL,
    email            VARCHAR(255),
    patient_number   VARCHAR(255)                            NOT NULL,
    date_of_birth    date                                    NOT NULL,
    date_of_passing  date,
    address_id       BIGINT                                  NOT NULL,
    CONSTRAINT pk_patient PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS visit
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    description VARCHAR(255),
    time        TIMESTAMP                               NOT NULL,
    doctor_id   BIGINT,
    patient_id  BIGINT,
    CONSTRAINT pk_visit PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS medical_treatment
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    description VARCHAR(255)                            NOT NULL,
    type        VARCHAR(255),
    visit_id    BIGINT,
    CONSTRAINT pk_medical_treatment PRIMARY KEY (id)
);


ALTER TABLE doctor
    ADD CONSTRAINT IF NOT EXISTS uc_doctor_address UNIQUE (address_id);

ALTER TABLE patient
    ADD CONSTRAINT IF NOT EXISTS uc_patient_address UNIQUE (address_id);

ALTER TABLE doctor
    ADD CONSTRAINT IF NOT EXISTS FK_DOCTOR_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE medical_treatment
    ADD CONSTRAINT IF NOT EXISTS FK_MEDICAL_TREATMENT_ON_VISIT FOREIGN KEY (visit_id) REFERENCES visit (id);

ALTER TABLE patient
    ADD CONSTRAINT IF NOT EXISTS FK_PATIENT_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE visit
    ADD CONSTRAINT IF NOT EXISTS FK_VISIT_ON_DOCTOR FOREIGN KEY (doctor_id) REFERENCES doctor (id);

ALTER TABLE visit
    ADD CONSTRAINT IF NOT EXISTS FK_VISIT_ON_PATIENT FOREIGN KEY (patient_id) REFERENCES patient (id);


INSERT INTO ADDRESS (ID, ADDRESS_LINE1, ADDRESS_LINE2, CITY, POSTAL_CODE)
VALUES (901, '250 Hospital Drive', 'Building A', 'New York', '10001');

INSERT INTO ADDRESS (ID, ADDRESS_LINE1, ADDRESS_LINE2, CITY, POSTAL_CODE)
VALUES (902, '123 Main St', 'Apt 4B', 'Boston', '02108');

INSERT INTO ADDRESS (ID, ADDRESS_LINE1, ADDRESS_LINE2, CITY, POSTAL_CODE)
VALUES (903, '87 Parkway Ave', 'Floor 3', 'Miami', '33101');

INSERT INTO ADDRESS (ID, ADDRESS_LINE1, ADDRESS_LINE2, CITY, POSTAL_CODE)
VALUES (904, '789 Oak Road', NULL, 'Chicago', '60611');

INSERT INTO ADDRESS (ID, ADDRESS_LINE1, ADDRESS_LINE2, CITY, POSTAL_CODE)
VALUES (905, '456 Pine Avenue', 'Suite 300', 'Seattle', '98101');

INSERT INTO DOCTOR (ID, FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, EMAIL, DOCTOR_NUMBER, SPECIALIZATION, ADDRESS_ID)
VALUES (123, 'John', 'Smith', '123123123', 'jsmith@gmail.com', '123123', 'SURGEON', 901);

INSERT INTO DOCTOR (ID, FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, EMAIL, DOCTOR_NUMBER, SPECIALIZATION, ADDRESS_ID)
VALUES (124, 'Emily', 'Johnson', '555123456', 'ejohnson@hospital.com', '724591', 'GP', 902);

INSERT INTO DOCTOR (ID, FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, EMAIL, DOCTOR_NUMBER, SPECIALIZATION, ADDRESS_ID)
VALUES (125, 'Michael', 'Rodriguez', '555789012', 'mrodriguez@hospital.com', '835672', 'DERMATOLOGIST', 904);

INSERT INTO DOCTOR (ID, FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, EMAIL, DOCTOR_NUMBER, SPECIALIZATION, ADDRESS_ID)
VALUES (126, 'Laura', 'Chen', '555678901', 'lchen@hospital.com', '946783', 'OCULIST', 905);

INSERT INTO PATIENT (ID, FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, EMAIL, PATIENT_NUMBER, DATE_OF_BIRTH, ADDRESS_ID)
VALUES (22, 'Connor', 'Sullivan', '123999123', 'csulivan@gmail.com', '98730', '1990-08-03', 903);

INSERT INTO PATIENT (ID, FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, EMAIL, PATIENT_NUMBER, DATE_OF_BIRTH, ADDRESS_ID)
VALUES (23, 'Sarah', 'Williams', '555234567', 'swilliams@email.com', '43215', '1985-11-15', 902);

INSERT INTO PATIENT (ID, FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, EMAIL, PATIENT_NUMBER, DATE_OF_BIRTH, DATE_OF_PASSING, ADDRESS_ID)
VALUES (24, 'Robert', 'Chen', '555345678', 'rchen@email.com', '54327', '1978-04-22', '2025-03-01', 905);

INSERT INTO VISIT (ID, DESCRIPTION, TIME, DOCTOR_ID, PATIENT_ID)
VALUES (48, 'Follow-up', '2025-04-03 10:15:00', 123, 22);

INSERT INTO VISIT (ID, DESCRIPTION, TIME, DOCTOR_ID, PATIENT_ID)
VALUES (49, 'Checkup', '2025-03-15 09:30:00', 124, 23);

INSERT INTO VISIT (ID, DESCRIPTION, TIME, DOCTOR_ID, PATIENT_ID)
VALUES (50, 'Consultation', '2025-03-18 14:00:00', 125, 24);

INSERT INTO VISIT (ID, DESCRIPTION, TIME, DOCTOR_ID, PATIENT_ID)
VALUES (51, 'Examination', '2025-03-10 15:45:00', 126, 23);

INSERT INTO MEDICAL_TREATMENT (ID, DESCRIPTION, TYPE, VISIT_ID)
VALUES (101, 'Heart test', 'EKG', 49);

INSERT INTO MEDICAL_TREATMENT (ID, DESCRIPTION, TYPE, VISIT_ID)
VALUES (102, 'Abdominal scan', 'USG', 49);

INSERT INTO MEDICAL_TREATMENT (ID, DESCRIPTION, TYPE, VISIT_ID)
VALUES (103, 'Chest scan', 'RTG', 50);

INSERT INTO MEDICAL_TREATMENT (ID, DESCRIPTION, TYPE, VISIT_ID)
VALUES (104, 'Eye scan', 'USG', 51);

INSERT INTO MEDICAL_TREATMENT (ID, DESCRIPTION, TYPE, VISIT_ID)
VALUES (105, 'Bone scan', 'RTG', 48);