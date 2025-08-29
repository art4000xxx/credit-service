CREATE TABLE credit_application (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    loan_amount DECIMAL(19,2) NOT NULL,
                                    loan_term INT NOT NULL,
                                    income DECIMAL(19,2) NOT NULL,
                                    current_debt DECIMAL(19,2) NOT NULL,
                                    credit_score INT NOT NULL,
                                    status VARCHAR(255) DEFAULT 'в обработке'
);