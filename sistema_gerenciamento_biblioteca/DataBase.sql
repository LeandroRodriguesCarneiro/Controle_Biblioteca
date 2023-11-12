-- criacao do banco de dados
CREATE DATABASE IF NOT EXISTS biblioteca;

-- acessar banco de dados
USE biblioteca;

-- criacao das tabelas
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
-- triggers 
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
        DELETE FROM authors_books WHERE id_books = OLD.id;
        DELETE FROM genres_books WHERE id_books = OLD.id;
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
-- procedures
CREATE PROCEDURE SP_InsertAuthor(
    IN p_author_name VARCHAR(50)
    )
BEGIN
	INSERT INTO author (name) VALUES (p_author_name);
END//

CREATE PROCEDURE SP_InsertGenre(
    IN p_genre_name VARCHAR(50)
    )
BEGIN
	INSERT INTO genre (name) VALUES (p_genre_name);
END//

CREATE PROCEDURE SP_InsertPublisher(
    IN p_publisher_name VARCHAR(50)
)
BEGIN
    INSERT INTO publisher (name) VALUES (p_publisher_name);
END//

CREATE PROCEDURE SP_InsertBook(
    IN p_id_publisher INT,
    IN p_isbn varchar(13),
    IN p_title VARCHAR(50),
    IN p_year_publication YEAR,
    IN p_quantity INT
)
BEGIN
    INSERT INTO book (id_publisher, isbn, title, year_publication, quantity) VALUES (p_id_publisher, p_isbn, p_title, p_year_publication, p_quantity);
    
    SELECT LAST_INSERT_ID();
END//

CREATE PROCEDURE SP_InsertGenreBook(
    IN p_id_book INT, 
    IN p_id_genre INT
)
BEGIN
    INSERT INTO genres_books (id_books, id_genre) VALUES (p_id_book, p_id_genre);
END//

CREATE PROCEDURE SP_InsertAuthorBook(
    IN p_id_book INT, 
    IN p_id_author INT
)
BEGIN
	INSERT INTO authors_books (id_books,id_author) VALUES (p_id_book,p_id_author);
END//

CREATE PROCEDURE SP_InsertBook(
	IN p_name VARCHAR(50),
	IN p_numberRegistration LONG
)
BEGIN
    INSERT INTO student(number_registration, name, borrowed_books, DEBITS) VALUES (p_numberRegistration,p_name, 0, 0);
END//

CREATE PROCEDURE SP_InsertLoan(
	IN p_id_student INT,
	IN p_date_init DATE,
    IN p_date_end DATE
)
BEGIN
    INSERT INTO loan(id_student, date_init, date_end, status) VALUES (p_id_student, p_date_init,p_date_and,'Emprestimo');
END//

CREATE PROCEDURE SP_InsertBorrowedBooks(
	IN p_id_loan INT,
	IN p_id_book INT
)
BEGIN
    INSERT INTO borrowed_books (id_loan,id_book,status) VALUES (p_id_loan,p_id_book,'Emprestimo');
END//

CREATE PROCEDURE SP_UpdateAuthor(
    IN p_author_id INT,
    IN p_author_name VARCHAR(50)
)
BEGIN
    UPDATE author SET name = p_author_name WHERE id = p_author_id;
END//

CREATE PROCEDURE SP_UpdateGenre(
    IN p_genre_id INT,
    IN p_genre_name VARCHAR(50)
)
BEGIN
    UPDATE genre SET name = p_genre_name WHERE id = p_genre_id;
END//

CREATE PROCEDURE SP_UpdatePublisher(
    IN p_publisher_id INT,
    IN p_publisher_name VARCHAR(50)
)
BEGIN
    UPDATE publisher SET name = p_publisher_name WHERE id = p_publisher_id;
END//

CREATE PROCEDURE SP_UpdateGenreBook(
    IN p_genre_book_id INT,
    IN p_id_book INT,
    IN p_id_genre INT
)
BEGIN
    UPDATE genres_books SET id_book = p_id_book, id_genre = p_id_genre WHERE id = p_genre_book_id;
END//

CREATE PROCEDURE SP_UpdateAuthorBook(
    IN p_author_book_id INT,
    IN p_id_book INT,
    IN p_id_author INT
)
BEGIN
    UPDATE authors_books SET id_book = p_id_book, id_author = p_id_author WHERE id = p_author_book_id;
END//

CREATE PROCEDURE `SP_UpdateBook`(
    IN p_book_id INT,
    IN p_id_publisher INT,
    IN p_isbn VARCHAR(13),
    IN p_title VARCHAR(50),
    IN p_year_publication YEAR,
    IN p_quantity INT
)
BEGIN
    DECLARE set_added BOOLEAN;
    SET set_added = FALSE;

    SET @query = 'UPDATE book SET ';

    IF p_id_publisher IS NOT NULL THEN
        SET @query = CONCAT(@query, 'id_publisher = ', p_id_publisher, ', ');
        SET set_added = TRUE;
    END IF;

    IF p_isbn IS NOT NULL THEN
        SET @query = CONCAT(@query, 'isbn = ''', p_isbn, ''', ');
        SET set_added = TRUE;
    END IF;

    IF p_title IS NOT NULL THEN
        SET @query = CONCAT(@query, 'title = ''', p_title, ''', ');
        SET set_added = TRUE;
    END IF;

    IF p_year_publication IS NOT NULL THEN
        SET @query = CONCAT(@query, 'year_publication = ', p_year_publication, ', ');
        SET set_added = TRUE;
    END IF;

    IF p_quantity IS NOT NULL THEN
        SET @query = CONCAT(@query, 'quantity = ', p_quantity, ',  ');
        SET set_added = TRUE;
    END IF;

    IF set_added THEN
        SET @query = SUBSTRING(@query, 1, LENGTH(@query) - 3);

        SET @query = CONCAT(@query, '         WHERE id = ', p_book_id);

        PREPARE stmt FROM @query;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END IF;
END//

CREATE PROCEDURE SP_UpdateStudent(
    IN p_author_id INT,
    IN p_name VARCHAR(50),
	IN p_numberRegistration LONG,
    IN p_borrowed_books INT,
    IN p_DEBITS FLOAT
)
BEGIN
    DECLARE set_added BOOLEAN;
    SET set_added = FALSE;
	
    SET @query = 'UPDATE student SET ';
    
     IF p_name IS NOT NULL THEN
        SET @query = CONCAT(@query, 'name = ''', p_name, ''',  ');
        SET set_added = TRUE;
    END IF;

    IF p_numberRegistration IS NOT NULL THEN
        SET @query = CONCAT(@query, 'number_registration = ''', p_numberRegistration, ''',  ');
        SET set_added = TRUE;
    END IF;

    IF p_borrowed_books IS NOT NULL THEN
        SET @query = CONCAT(@query, 'borrowed_books = ', p_borrowed_books, ',  ');
        SET set_added = TRUE;
    END IF;

    IF p_DEBITS IS NOT NULL THEN
        SET @query = CONCAT(@query, 'DEBITS = ', p_DEBITS, ',  ');
        SET set_added = TRUE;
    END IF;

    IF set_added THEN
        SET @query = SUBSTRING(@query, 1, LENGTH(@query) - 3);

        SET @query = CONCAT(@query, '         WHERE id = ', p_author_id);

        PREPARE stmt FROM @query;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END IF;
END//
DELIMITER ;