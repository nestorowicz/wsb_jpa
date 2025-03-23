INSERT INTO ADDRESS (ID, ADDRESS_LINE1, ADDRESS_LINE2, CITY, POSTAL_CODE)
VALUES (901, '250 Hospital Drive', 'Building A', 'New York', '10001'),
       (902, '123 Main St', 'Apt 4B', 'Boston', '02108'),
       (903, '87 Parkway Ave', 'Floor 3', 'Miami', '33101'),
       (904, '789 Oak Road', NULL, 'Chicago', '60611'),
       (905, '456 Pine Avenue', 'Suite 300', 'Seattle', '98101');

INSERT INTO DOCTOR (ID, FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, EMAIL, DOCTOR_NUMBER, SPECIALIZATION, ADDRESS_ID)
VALUES (123, 'John', 'Smith', '123123123', 'jsmith@gmail.com', '123123', 'SURGEON', 901),
       (124, 'Emily', 'Johnson', '555123456', 'ejohnson@hospital.com', '724591', 'GP', 902),
       (125, 'Michael', 'Rodriguez', '555789012', 'mrodriguez@hospital.com', '835672', 'DERMATOLOGIST', 904),
       (126, 'Laura', 'Chen', '555678901', 'lchen@hospital.com', '946783', 'OCULIST', 905);

INSERT INTO PATIENT (ID, FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, EMAIL, PATIENT_NUMBER, DATE_OF_BIRTH, DATE_OF_PASSING, ADDRESS_ID)
VALUES (22, 'Connor', 'Sullivan', '123999123', 'csulivan@gmail.com', '98730', '1990-08-03', NULL, 903),
       (23, 'Sarah', 'Williams', '555234567', 'swilliams@email.com', '43215', '1985-11-15', NULL, 902),
       (24, 'Robert', 'Chen', '555345678', 'rchen@email.com', '54327', '1978-04-22', '2025-03-01', 905);

INSERT INTO VISIT (ID, DESCRIPTION, TIME, DOCTOR_ID, PATIENT_ID)
VALUES (48, 'Follow-up', '2025-04-03 10:15:00', 123, 22),
       (49, 'Checkup', '2025-03-15 09:30:00', 124, 23),
       (50, 'Consultation', '2025-03-18 14:00:00', 125, 24),
       (51, 'Examination', '2025-03-10 15:45:00', 126, 23);

INSERT INTO MEDICAL_TREATMENT (ID, DESCRIPTION, TYPE, VISIT_ID)
VALUES (101, 'Heart test', 'EKG', 49),
       (102, 'Abdominal scan', 'USG', 49),
       (103, 'Chest scan', 'RTG', 50),
       (104, 'Eye scan', 'USG', 51),
       (105, 'Bone scan', 'RTG', 48);