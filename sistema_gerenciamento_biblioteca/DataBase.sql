CREATE DATABASE IF NOT EXISTS biblioteca;

USE biblioteca;

CREATE TABLE IF NOT EXISTS genre(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    
    CONSTRAINT UK_GENRE_NAME UNIQUE (name)
    );
    
CREATE TABLE IF NOT EXISTS author(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    
    CONSTRAINT UK_AUTHOR_NAME UNIQUE (name)
    );

CREATE TABLE IF NOT EXISTS publisher(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    
    CONSTRAINT UK_PUBLISHER_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS book(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_publisher INT NOT NULL,
    isbn varchar(13) NOT NULL UNIQUE,
    title varchar(50) NOT NULL,
    year_publication YEAR NOT NULL,
    quantity int NOT NULL,
    
    CONSTRAINT UK_BOOK_ISBN UNIQUE (isbn),
    CONSTRAINT FK_BOOKS_PUBLISHER FOREIGN KEY (id_publisher ) REFERENCES publisher(id)
    );
    
CREATE TABLE IF NOT EXISTS authors_books(
     id INT AUTO_INCREMENT PRIMARY KEY,
    id_books INT NOT NULL,
    id_author INT NOT NULL,
    
    CONSTRAINT FK_AUTHORSBOOKS_BOOK FOREIGN KEY (id_books) REFERENCES book(id),
    CONSTRAINT FK_AUTHORSBOOKS_AUTHOR FOREIGN KEY (id_author) REFERENCES author(id)
    );
    
CREATE TABLE IF NOT EXISTS genres_books(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_books INT NOT NULL,
    id_genre INT NOT NULL,
    
    CONSTRAINT FK_GENDERSBOOKS_BOOK FOREIGN KEY (id_books) REFERENCES book(id),
    CONSTRAINT FK_GENDERSBOOKS_GENDER FOREIGN KEY (id_genre) REFERENCES genre(id)
    );
    
CREATE TABLE IF NOT EXISTS student(
    id INT AUTO_INCREMENT PRIMARY KEY,
    number_registration VARCHAR(10) NOT NULL,
    name VARCHAR(50) NOT NULL,
    borrowed_books INT  NOT NULL,
    DEBITS FLOAT(4,2) NOT NULL,

    CONSTRAINT UK_STUDENT_NUMBER_REGISTRATION UNIQUE (number_registration)
    );
    
CREATE TABLE IF NOT EXISTS loan(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_student INT NOT NULL,
    date_init DATE NOT NULL,
    date_end DATE NOT NULL,
    status VARCHAR(10) NOT NULL,
    CONSTRAINT FK_LOAN_STUDENT FOREIGN KEY (id_student) REFERENCES student(id)
    );

CREATE TABLE IF NOT EXISTS borrowed_books(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_loan INT NOT NULL,
    id_book INT NOT NULL,
    status VARCHAR(10) NOT NULL,
    CONSTRAINT FK_BORROWEDBOOKS_LOAN FOREIGN KEY (id_loan) REFERENCES loan(id),
    CONSTRAINT FK_BORROWEDBOOKS_BOOKS FOREIGN KEY (id_book) REFERENCES book(id)
    );

DELIMITER //
CREATE TRIGGER TRG_book_BF_DEL 
BEFORE DELETE ON book FOR EACH ROW
BEGIN
    DECLARE custom_error CONDITION FOR SQLSTATE '45000';
	DECLARE existing_id INT;
    
    SELECT id INTO existing_id FROM borrowed_books WHERE id_book = OLD.id LIMIT 1;
    
    IF (existing_id) IS NOT NULL THEN 
		SIGNAL custom_error
            SET MESSAGE_TEXT = 'Tem emprestimos relacionados a esse livro';
    ELSE
        DELETE FROM authors_books WHERE id_book = OLD.id;
        DELETE FROM genres_books WHERE id_book = OLD.id;
    END IF;
END//

CREATE TRIGGER TRG_student_BF_DEL 
BEFORE DELETE ON student FOR EACH ROW
BEGIN
    DECLARE custom_error CONDITION FOR SQLSTATE '45000';
	DECLARE existing_id INT;
    
    SELECT id INTO existing_id FROM loan WHERE loan.id_student = 1 LIMIT 1;    
    IF (existing_id) IS NOT NULL THEN 
		SIGNAL custom_error
            SET MESSAGE_TEXT = 'Tem emprestimos relacionados a esse aluno';
    END IF;
END//

CREATE TRIGGER TRG_borrowed_books_AF_INS 
AFTER INSERT ON borrowed_books FOR EACH ROW
BEGIN
    DECLARE custom_error CONDITION FOR SQLSTATE '45000';

    IF (SELECT quantity FROM book WHERE id = NEW.id_book) > 0 THEN 
        UPDATE book SET quantity = (quantity - 1) WHERE id = NEW.id_book;

        UPDATE student
        LEFT JOIN loan ON NEW.id_loan = loan.id AND loan.id_student = student.id
        SET student.borrowed_books = (student.borrowed_books + 1)
        WHERE student.id = loan.id_student;
    ELSE
        SIGNAL custom_error
            SET MESSAGE_TEXT = 'Nao ha livros disponiveis para emprestar';
    END IF;
END//

CREATE TRIGGER TRG_borrowed_books_AF_UPDATE 
AFTER UPDATE ON borrowed_books FOR EACH ROW
BEGIN
	DECLARE custom_error CONDITION FOR SQLSTATE '45000';
	IF (NEW.status = 'Devolvido') THEN
		UPDATE book SET quantity = (quantity + 1) WHERE id = NEW.id_book;
        UPDATE student
        LEFT JOIN loan ON NEW.id_loan = loan.id AND loan.id_student = student.id
        SET student.borrowed_books = (student.borrowed_books - 1)
        WHERE student.borrowed_books > 0 AND student.id = loan.id_student; 
    END IF;
END//
DELIMITER ;