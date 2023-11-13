package Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;
import java.time.LocalDate;


import Author.Author;
import Author.AuthorDAO;
import Genres.Genres;
import Genres.GenresDAO;
import Publisher.Publisher;
import Publisher.PublisherDAO;
import Student.Student;
import Student.StudentDAO;
import Loan.Loan;
import Loan.LoanDAO;

public class AdminsterBilioeteca {

	public static void main(String[] args) {
		AdminsterBilioeteca adm = new  AdminsterBilioeteca();
		adm.menu();
	}	
		public void menu() {
		    int option;
		    Scanner in = new Scanner(System.in);
	
		    do {
		        System.out.println("Digite: \n\t"
		                + "1 para entrar no menu de Livros\n\t"
		                + "2 para entrar no menu de Alunos\n\t"
		                + "3 para ir para o menu de Empréstimos\n\t"
		                + "4 para relatorios\n\t"
		                + "0 para sair\n");
	
		        option = in.nextInt();
		        in.nextLine();  // Limpa o buffer após a leitura do número
	
		        switch (option) {
		            case 1:
		                menuLivro(in);
		                break;
		            case 2:
		                menuAlunos(in);
		                break;
		            case 3:
		                menuEmprestimos(in);
		                break;
		            case 4:
		            	menuRelatorios(in);
		            	break;
		        }
		    } while (option != 0);
	
		    in.close();
		}
		public void menuLivro(Scanner in) {
		    int option = -1;
		    do {
		        System.out.println("Digite: \n\t"
		                + "1 para menu de gêneros literários\n\t"
		                + "2 para menu de autores de livros\n\t"
		                + "3 para menu de editoras\n\t"
		                + "4 para menu de livros\n\t"
		                + "0 para sair\n");

		        if (in.hasNextInt()) {
		            option = in.nextInt();

		            in.nextLine();
		        } else {
		            in.nextLine();
		            System.out.println("Digite um número válido!");
		            continue;
		        }

		        switch (option) {
		            case 1:
		                menuGenero(in);
		                break;
		            case 2:
		                menuAutor(in);
		                break;
		            case 3:
		                menuEditoras(in);
		                break;
		            case 4:
		                menuLivros(in);
		                break;
		        }
		    } while (option != 0);
		}
		public void menuGenero(Scanner in) {
		    int option = -1; 
		    do {
		        System.out.println("Digite: \n\t"
		                + "1 para adicionar gêneros literários \n\t"
		                + "2 para listar gêneros \n\t"
		                + "0 para sair\n");

		        if (in.hasNextInt()) {
		            option = in.nextInt();
		            in.nextLine();
		        } else {
		            in.nextLine();
		            System.out.println("Digite um número válido!");
		            continue; 
		        }

		        switch (option) {
		            case 1:
		                adicionarGeneros(in);
		                break;
		            case 2:
		                listarGeneros();
		                break;
		            }
		    } while (option != 0);
		}
	
		public void adicionarGeneros(Scanner in) {
		    int option = -1;

		    do {
		        System.out.println("Digite o nome do gênero que deseja adicionar:");
		        String genre = in.nextLine().trim();

		        if (!genre.isEmpty()) {
		            GenresDAO genresDAO = new GenresDAO();
		            genresDAO.insertGenres(genre);
		        } else {
		            System.out.println("Nome do gênero não pode estar vazio.");
		        }

		        System.out.println("Se deseja adicionar um novo gênero, digite 1; digite 0 para voltar ao menu anterior:");
		        
		        if (in.hasNextInt()) {
		            option = in.nextInt();
		            in.nextLine();
		        } else {
		            in.nextLine();
		            System.out.println("Digite um número válido!");
		            continue;
		        }

		    } while (option != 0);
		}
		public void listarGeneros() {
			GenresDAO genresDAO = new GenresDAO();
			List<Genres> genresList = genresDAO.selectAllGenres();
			for (Genres genres : genresList) {
	            System.out.println("ID: " + genres.getId() + ", Nome: " + genres.getName());
	        }
		}
		
		public void menuAutor(Scanner in) {
		    int option = -1;

		    do {
		        System.out.println("Digite: \n\t"
		                + "1 para adicionar autores \n\t"
		                + "2 para para listar autores \n\t"
		                + "0 para sair\n");

		        if (in.hasNextInt()) {
		            option = in.nextInt();
		            in.nextLine();
		        } else {
		            in.nextLine();
		            System.out.println("Digite um número válido!");
		            continue;
		        }
		        switch(option) {
		            case 1:{
		                adicionarAutores(in);
		                break;
		            }
		            case 2:{
		                listarAutores();
		                break;
		            }
		        }
		    } while (option != 0);
		}
		public void adicionarAutores(Scanner in) {
		    int option = -1;

		    do {
		        System.out.println("Digite o nome do autor que deseja adicionar:");
		        String autor = in.nextLine().trim();
		        in.nextLine();

		        if (!autor.isEmpty()) {
		            AuthorDAO authorDAO = new AuthorDAO();
		            authorDAO.insertAuthor(autor);
		        } else {
		            System.out.println("Nome do autor não pode estar vazio.");
		        }

		        System.out.println("Se deseja adicionar um novo autor, digite 1; digite 0 para voltar ao menu anterior:");

		        if (in.hasNextInt()) {
		            option = in.nextInt();
		            in.nextLine();
		        } else {
		            in.nextLine();
		            System.out.println("Digite um número válido!");
		            continue;  
		        }

		    } while (option != 0);
		}
		public void listarAutores() {
			AuthorDAO authorDAO = new AuthorDAO();
			List<Author> genresList = authorDAO.selectAllAuthors();
			for (Author author : genresList) {
	            System.out.println("ID: " + author.getId() + ", Nome: " + author.getName());
	        }
		}

		public void menuEditoras(Scanner in) {
		    int option = -1;
		    do {
		        System.out.println("Digite: \n\t"
		                + "1 para adicionar editoras \n\t"
		                + "2 para listar editoras \n\t"
		                + "0 para sair\n");

		        if (in.hasNextInt()) {
		            option = in.nextInt();
		            in.nextLine();
		        } else {
		            in.nextLine();
		            System.out.println("Digite um número válido!");
		            continue;
		        }

		        switch(option) {
		            case 1:{
		                adicionarEditoras(in);
		                break;
		            }
		            case 2:{
		                listarEditoras();
		                break;
		            }
		        }
		    } while (option != 0);
		}
		public void adicionarEditoras(Scanner in) {
		    int option =-1;
		    do {
		    	System.out.println("Digite o nome da editora que deseja adicionar:");
		        String editora = in.nextLine().trim();

		        if (!editora.isEmpty()) {
		            PublisherDAO publisherDAO = new PublisherDAO();
		            publisherDAO.insertPublisher(editora);
		        } else {
		            System.out.println("Nome da editora não pode estar vazio.");
		        }

		        System.out.println("Se deseja adicionar uma nova editora, digite 1; digite 0 para voltar ao menu anterior:");
		        if (in.hasNextInt()) {
		            option = in.nextInt();
		            in.nextLine();
		        } else {
		            in.nextLine();
		            System.out.println("Digite um número válido!");
		            continue;  
		        }
		    } while (option != 0);
		}
		public void listarEditoras() {
			PublisherDAO  publisherDAO= new PublisherDAO();
			List<Publisher> publisherList = publisherDAO.selectAllPublisher();
			for (Publisher publisher : publisherList) {
	            System.out.println("ID: " + publisher.getId() + ", Nome: " + publisher.getName());
	        }
		}
		
		public void menuLivros(Scanner in) {
		    int option = -1;

		    do {
		        System.out.println("Digite: \n\t"
		                + "1 para adicionar livros \n\t"
		                + "2 para listar livros \n\t"
		                + "3 para alterar livros \n\t"
		                + "4 para apagar livros \n\t"
		                + "0 para sair\n");
		        if (in.hasNextInt()) {
		            option = in.nextInt();
		            in.nextLine();
		        } else {
		            in.nextLine();
		            System.out.println("Digite um número válido!");
		            continue;
		        }

		        switch (option) {
		            case 1:
		                adicionarLivros(in);
		                break;
		            case 2:
		                listarLIvros();
		                break;
		            case 3:
		                alterarLivros(in);
		                break;
		            case 4:
		                deletarLivros(in);
		                break;
		            case 0:
		                System.out.println("Saindo do menu de livros...");
		                break;
		        }
		    } while (option != 0);
		}
		public void adicionarLivros(Scanner in) {
			PublisherDAO publisherDAO = new PublisherDAO();
			List<Publisher> publisherList = publisherDAO.selectPublisherByCountTitles10();
			Publisher publisher = null;
	
			System.out.println("Aqui estão algumas opções de editoras. Se a editora estiver na lista, digite seu ID; caso contrário, digite 0");
			for (Publisher p : publisherList) {
			    System.out.println("\t ID: " + p.getId() + ", Nome: " + p.getName());
			}
	
			int option = in.nextInt();
			in.nextLine();
	
			if (option != 0) {
			    for (Publisher p : publisherList) {
			        if (p.getId() == option) {
			            publisher = p;
			            break;
			        }
			    }
			} else {
			    publisherList.clear();
			    System.out.println("Digite o nome da Editora:");
			    String search = in.nextLine().trim();
			    publisherList = publisherDAO.selectPublisherByName(search);
	
			    if (publisherList.isEmpty()) {
			        System.out.println("Não há registros");
			    } else {
			        for (Publisher p : publisherList) {
			            System.out.println("\t ID: " + p.getId() + ", Nome: " + p.getName());
			        }
	
			        System.out.println("Digite o ID da editora; se não estiver na lista, digite 0");
			        option = in.nextInt();
			        in.nextLine();
	
			        if (option != 0) {
			            for (Publisher p : publisherList) {
			                if (p.getId() == option) {
			                    publisher = p;
			                    break;
			                }
			            }
			        }
			    }
		        System.out.println("Deseja adicionar uma nova editora? Digite 1 para sim, 0 para não");
		        option = in.nextInt();
		        in.nextLine();
	
		        if (option == 1) {
		            System.out.println("Digite o nome da editora que deseja adicionar:");
		            String newPublisherName = in.nextLine().trim();
		            if (!newPublisherName.isEmpty()) {
		                int newPublisherId = publisherDAO.insertPublisher(newPublisherName);
		                publisher = new Publisher(newPublisherId, newPublisherName);
		            } else {
		                System.out.println("O nome da editora não pode estar vazio.");
		            }
		        }
			}
	
			String isbn;
			do {
			    System.out.println("Digite o ISBN do livro sem traços");
			    isbn = in.nextLine();
	
			    if (isbn.length()<13) {
			        System.out.println("O ISBN informado não é válido");
			    }
			    in.nextLine();
			} while (isbn.length() != 13);
			
			String title;
			do {
				 System.out.println("Digite o titulo do livro");
				title = in.nextLine();
				
				if(title.isEmpty()) {
					System.out.println("Precisa informar um titulo");
				}
				in.nextLine();
			}while(title.isEmpty());
			
			Year yearPublication = null;
			do {
		           try {
		               System.out.println("Digite o ano de publicacao do livro:");
		               int inputYear = in.nextInt();
		               yearPublication = Year.of(inputYear);
		               in.nextLine();
		           } catch (DateTimeException e) {
		               System.out.println("Ano inválido. Digite um ano válido.");
		               in.nextLine();
		           }
		    } while (yearPublication == null);
			
			int quantity = 0;
			do {
				System.out.println("Digite a quantidade de livros:");
				quantity = in.nextInt();
				if(quantity<=0) {
					System.out.println("A quantidade deve ser maior que 0");
					in.nextLine();
				}
			}while(quantity<=0);
			option = 0;
			GenresDAO genresDAO = new GenresDAO();
			List<Genres> genresList = genresDAO.selectAllGenres();
			List<Genres> genresBooksList = new ArrayList<>();
			do {
				for (Genres genres: genresList) {
					System.out.println("ID : "+genres.getId()+" Nome:"+ genres.getName());
				}
				System.out.println("Digite o id do genero que deseja adicionar ou digite 0 para ir para a proxima etapa");
				option = in.nextInt(); 
				for (Genres genres: genresList) {
					if(genres.getId() == option) {
						genresBooksList.add(genres);
					}
				}
			}while(option > 0 | genresBooksList.isEmpty());
			
			AuthorDAO authorDAO = new AuthorDAO();
			List<Author> authorList = authorDAO.selectAllAuthors();
			List<Author> authorBooksList = new ArrayList<>();
			do {
				for (Author author: authorList) {
					System.out.println("ID : "+author.getId()+" Nome:"+ author.getName());
				}
				System.out.println("Digite o id do author que deseja adicionar ou digite 0 para ir para a proxima etapa");
				option = in.nextInt(); 
				for (Author author: authorList) {
					if(author.getId() == option) {
						authorBooksList.add(author);
					}
				}
			}while(option > 0 | genresBooksList.isEmpty());
			
			BookDAO bookDAO = new BookDAO();
			bookDAO.insertBook(publisher.getId(), isbn, title, yearPublication, quantity, genresBooksList,authorBooksList);
		}
		public void listarLIvros() {
			BookDAO bookDAO = new BookDAO();
			List<Book> bookList = bookDAO.selectAllBooks();
			for (Book book: bookList) {
				System.out.println("id: "+book.getId());
				System.out.println("isbn: "+book.getIsbn());
				System.out.println("titulo: "+book.getTitle());
				System.out.println("editora: "+book.getPublisher());
				System.out.println("quantidade: "+book.getQuantity());
				System.out.println("ano de publicacao: "+book.getYearPublication());
				System.out.println("generos: ");
				for (String string: book.getGenre()) {
					System.out.println("	"+string);
				}
				System.out.println("Autores: ");
				for (String string: book.getAuthor()) {
					System.out.println("	"+string);
				}
			}
		}
		public void alterarLivros(Scanner in) {
			int option = 0;
			String search;
			BookDAO bookDAO = new BookDAO();
			
			do {
				System.out.println("Digite o ISBN do livro que deseja selecionar apenas numeros");
				search = in.nextLine();
				List<Book> bookList = bookDAO.selectBooksByISBN(search);
				for (Book book: bookList) {
					System.out.println("id: "+book.getId());
					System.out.println("isbn: "+book.getIsbn());
					System.out.println("titulo: "+book.getTitle());
					System.out.println("editora: "+book.getPublisher());
					System.out.println("quantidade: "+book.getQuantity());
					System.out.println("ano de publicacao: "+book.getYearPublication());
					System.out.println("generos: ");
					for (String string: book.getGenre()) {
						System.out.println("	"+string);
					}
					System.out.println("Autores: ");
					for (String string: book.getAuthor()) {
						System.out.println("	"+string);
					}
				}
				System.out.println("Digite "
						+ "1 para alterar o titulo"
						+ "2 para alterar a editora"
						+ "3 para alterar a quantidade"
						+ "4 para alterar os generos");
				option = in.nextInt();
				// continar a logica para alterar os dados do livro
			}while(option != 0);
			
		}
		public void deletarLivros(Scanner in) {
			in.nextLine();
			String search;
			BookDAO bookDAO = new BookDAO();
			System.out.println("Digite o ISBN do livro que deseja selecionar apenas numeros");
			search = in.nextLine();
			List<Book> bookList = bookDAO.selectBooksByISBN(search);
			if(bookList.size() == 1) {
				//bookDAO.deleteBook(bookList.get(0).getId());
				for (Book book: bookList) {
					System.out.println("id: "+book.getId());
					System.out.println("isbn: "+book.getIsbn());
					System.out.println("titulo: "+book.getTitle());
					System.out.println("editora: "+book.getPublisher());
					System.out.println("quantidade: "+book.getQuantity());
					System.out.println("ano de publicacao: "+book.getYearPublication());
					System.out.println("generos: ");
					for (String string: book.getGenre()) {
						System.out.println("	"+string);
					}
					System.out.println("Autores: ");
					for (String string: book.getAuthor()) {
						System.out.println("	"+string);
					}
					}
			}else {
				System.out.println("Nao foi encontrado o livro");
			}
		}

		public void menuAlunos(Scanner in) {
			int option;
			do {
				System.out.println("Digite: \n\t"
						+ "1 para adicionar Alunos\n\t"
						+ "2 para consultar Alunos\n\t"
						+ "3 para alterar Alunos\n\t"
						+ "4 para deletar Alunos \n\t"
						+ "0 para sair\n");
				option = in.nextInt();
				switch(option) {
				case 1:{
					adicionarAlunos(in);
					break;
				}
				case 2:{
					consultarAlunos(in);
					break;
				}
				case 3:{
					alterarAlunos(in);
					break;
				}
				case 4:{
					deletarAlunos(in);
					break;
				}
				}
			}while(option != 0);
			menu();
		}
		public void adicionarAlunos(Scanner in) {
		    String nome;
		    int confirmacao;
		    in.nextLine();
		    do {
		        System.out.println("Digite o nome do Aluno: ");
		        nome = in.nextLine();
		        System.out.println("O nome: " + nome + " digite 1 para confirmar ou 0 para reescrever");
		        confirmacao = in.nextInt();
		    } while (confirmacao !=1);

		    long numberRegistration;
		    do {
		        System.out.println("Digite o Numero de matricula: ");
		        numberRegistration = in.nextLong();
		        in.nextLine();
		        System.out.println("O Numero de matricula: " + numberRegistration + " digite 1 para confirmar ou 0 para reescrever");
		        confirmacao = in.nextInt();
		    } while (confirmacao !=1);
		    StudentDAO studentDAO = new StudentDAO();
		    studentDAO.insertStudent(nome, numberRegistration);
		}
		public void consultarAlunos(Scanner in) {
			in.nextLine();
			long numberRegistration;
			System.out.println("Digite o Numero de matricula: ");
			numberRegistration = in.nextLong();
			StudentDAO studentDAO = new StudentDAO();
			Student student =  studentDAO.selectStudentByNumerRegistration(numberRegistration).get(0);
			System.out.println("id: "+student.getId());
			System.out.println("nome: "+student.getName());
			System.out.println("numero de matricula: "+student.getNumberRegistration());
			System.out.println("debitos: "+student.getDebits());
			int option = 0;
			if(student.getDebits() > 0) {
				System.out.println("Deseja quitar a divida digite 1 para sim e 0 para nao: ");
				option = in.nextInt();
				if(option == 1) {
					quitarDivida(in,student.getId(),student.getDebits());
				}
			}
		}
		public void alterarAlunos(Scanner in) {
			
		}
		public void deletarAlunos(Scanner in) {
			in.nextLine();
			long numberRegistration;
			int option, id = 0;
			System.out.println("Digite o Numero de matricula: ");
			numberRegistration = in.nextLong();
			StudentDAO studentDAO = new StudentDAO();
			List<Student> listStudent = studentDAO.selectStudentByNumerRegistration(numberRegistration);
			for(Student student: listStudent) {
				id = student.getId();
				System.out.println("id: "+student.getId());
				System.out.println("nome: "+student.getName());
				System.out.println("numero de matricula: "+student.getNumberRegistration());
				System.out.println("debitos: "+student.getDebits());
			}
			System.out.println("Digite 1 para confirmar e apagar ou 0 para cancelar");
			option = in.nextInt();
			if(option == 1 && listStudent.size() >= 1) {
				studentDAO.deleteStudent(id);
			}
		}
		public void quitarDivida(Scanner in, int idStudent, float debits) {
		    in.nextLine();
		    float pay = 0; // Inicializa com 0

		    StudentDAO studentDAO = new StudentDAO();
		    System.out.println("O aluno deve R$" + debits*(-1) + ": ");
	        pay = in.nextFloat();
	        if (pay > 0) {
	            if (pay >= (debits*(-1))) {
	                studentDAO.payDebits(idStudent, (debits * (-1)));
	                System.out.println("Dívida quitada. Troco: " + (pay + debits));
	            } else {
	                studentDAO.payDebits(idStudent, pay);
	                System.out.println("O pagamento não é suficiente para quitar a dívida. A dívida é de: " + ((debits*(-1)) - pay));
	            }
	        } else {
	            System.out.println("O pagamento deve ser maior que zero.");
	        }
		}
		
		public void menuEmprestimos(Scanner in) {
				int option;
				do {
					System.out.println("Digite: \n\t"
							+ "1 para realizar novos emprestimos\n\t"
							+ "2 para consultar emprestimos \n\t"
							+ "3 para devolver livro\n\t"
							+ "0 para sair\n");
					option = in.nextInt();
					switch(option) {
					case 1:{
						realizarEmprestimos(in);
						break;
					}
					case 2:{
						consultarLivro(in);
						break;
					}
					case 3:{
						devolverLivro(in);
						break;
					}
					}
				}while(option != 0);
				menu();
			}
		public void realizarEmprestimos(Scanner in) {
			in.nextLine();
			long numberRegistration;
			int option;
			List <Student> listStudent = new ArrayList<>();
			StudentDAO studentDAO = new StudentDAO();
			Student student = null;
			do {
				System.out.println("Digite o número de matrícula: ");
	            numberRegistration = in.nextLong();
	            in.nextLine(); 
	            
	            System.out.println("Você digitou o numero " + numberRegistration + ". Digite 1 para confirmar ou 0 para digitar novamente.");
	            option = in.nextInt();
	            if(option == 1) {
	            	listStudent = studentDAO.selectStudentByNumerRegistration(numberRegistration);
		            if(!listStudent.isEmpty()) {
		            	student = listStudent.get(0);
		            }else {
		            	option = 0;
		            	System.out.println("Aluno nao encontrado digite novamente");
		            }
	            }
	            
	            in.nextLine(); 
			}while(option != 1);
			option = 0;
			if(student.getDebits() < 0) {
				System.out.println("O aluno deve R$"+student.getDebits()*(-1)+"\n"
						+ "Deseja quitar a divida digite 1 para sim e 0 para nao: ");
				option = in.nextInt();
				if(option == 1) {
					quitarDivida(in,student.getId(),student.getDebits());
					student = studentDAO.selectStudentByNumerRegistration(numberRegistration).get(0);
					if(student.getDebits() < 0) {
						System.out.println("Nao sera possivel realizar emprestimos ate quitar a divida");
						return;
					}
				}else {
					System.out.println("Nao sera possivel realizar emprestimos ate quitar a divida");
					return;
				}
			}
			if(student.getBorrowedBooks() >= 1) {
				System.out.println("Precisa devolver os livors para poder emprestar outros");
				return;
			}
			BookDAO bookDAO = new BookDAO();
			option = 0;
			String isbn;
			List<Book> listBooks = new ArrayList<>();
			do {
		        System.out.println("Digite o ISBN do livro sem tracos: ");
		        isbn = in.nextLine();

		        System.out.println("Digite 1 para confirmar o livro ou 0 para digitar novamente: ");
		        int confirmOption = in.nextInt();
		        in.nextLine(); // Limpar o buffer do scanner

		        if (confirmOption == 1) {
		            List<Book> selectedBook = bookDAO.selectBooksByISBN(isbn);

		            if (!selectedBook.isEmpty()) {
		                listBooks.add(selectedBook.get(0));
		                System.out.println("Livro adicionado ao emprestimo: " + selectedBook.get(0).getTitle());
		            } else {
		                System.out.println("Livro nao encontrado. Por favor, insira um ISBN valido.");
		            }
		        }
		        if(listBooks.size() < 3) {
		        	System.out.println("Digite 0 para adicionar outro livro ou digite 1 para ir para o proximo passo: ");
			        option = in.nextInt();
		        }else {
		        	System.out.println("Digite 1 para ir para o proximo passo: ");
			        option = in.nextInt();
		        }
		        
		        in.nextLine(); 
		    } while (option != 1);
			LoanDAO loanDAO = new LoanDAO();
			int days;
			LocalDate endDate = null;
			do {
		        System.out.println("Digite quantos dias deseja emprestar o livro (mínimo 1 e máximo 15): ");
		        days = in.nextInt();
		        in.nextLine(); // Limpar o buffer do scanner

		        if (days >= 1 && days <= 15) {
		            endDate = LocalDate.now().plusDays(days);
		        } else {
		            System.out.println("O prazo do emprestimo deve estar entre 1 e 15 dias.");
		        }
		    } while (days < 1 || days > 15);
			loanDAO.insertLoan(student.getId(), LocalDate.now(), endDate, listBooks);
		}
		public void consultarLivro(Scanner in) {
			long numberRegistration;
			int option;
			List <Student> listStudent = new ArrayList<>();
			StudentDAO studentDAO = new StudentDAO();
			Student student = null;
			do {
				System.out.println("Digite o número de matrícula: ");
	            numberRegistration = in.nextLong();
	            in.nextLine(); 
	            
	            System.out.println("Você digitou o numero " + numberRegistration + ". Digite 1 para confirmar ou 0 para digitar novamente.");
	            option = in.nextInt();
	            if(option == 1) {
	            	listStudent = studentDAO.selectStudentByNumerRegistration(numberRegistration);
		            if(!listStudent.isEmpty()) {
		            	student = listStudent.get(0);
		            }else {
		            	option = 0;
		            	System.out.println("Aluno nao encontrado digite novamente");
		            }
	            }
	            
	            in.nextLine(); 
			}while(option != 1);
			
			LoanDAO loanDAO = new LoanDAO();
			Loan loan;
			List <Loan> listLoan = loanDAO.selectLoanBooks(student.getId());
			if(!listLoan.isEmpty()) {
				loan = listLoan.get(0);
			}else {
				System.out.println("O aluno nao contem emprestimos");
				return;
			}
			for (BooksBorrowed book: loan.getListBooks()) {
				System.out.println("id: "+book.getId());
				System.out.println("isbn: "+book.getIsbn());
				System.out.println("titulo: "+book.getTitle());
				System.out.println("editora: "+book.getPublisher());
				System.out.println("quantidade: "+book.getQuantity());
				System.out.println("ano de publicacao: "+book.getYearPublication());
				System.out.println("generos: ");
				for (String string: book.getGenre()) {
					System.out.println("	"+string);
				}
				System.out.println("Autores: ");
				for (String string: book.getAuthor()) {
					System.out.println("	"+string);
				}
			}
		}
		public void devolverLivro(Scanner in) {
			long numberRegistration;
			int option;
			List <Student> listStudent = new ArrayList<>();
			StudentDAO studentDAO = new StudentDAO();
			Student student = null;
			do {
				System.out.println("Digite o número de matrícula: ");
	            numberRegistration = in.nextLong();
	            in.nextLine(); 
	            
	            System.out.println("Você digitou o numero " + numberRegistration + ". Digite 1 para confirmar ou 0 para digitar novamente.");
	            option = in.nextInt();
	            if(option == 1) {
	            	listStudent = studentDAO.selectStudentByNumerRegistration(numberRegistration);
		            if(!listStudent.isEmpty()) {
		            	student = listStudent.get(0);
		            }else {
		            	option = 0;
		            	System.out.println("Aluno nao encontrado digite novamente");
		            }
	            }
	            
	            in.nextLine(); 
			}while(option != 1);
			
			LoanDAO loanDAO = new LoanDAO();
			Loan loan;
			List <Loan> listLoan = loanDAO.selectLoanBooks(student.getId());
			if(!listLoan.isEmpty()) {
				loan = listLoan.get(0);
			}else {
				System.out.println("O aluno nao contem emprestimos");
				return;
			}
			List <Book> listBooks = new ArrayList<>();
			String isbn;
			BookDAO bookDAO = new BookDAO();
			do {
		        System.out.println("Digite o ISBN do livro sem traços: ");
		        isbn = in.nextLine();

		        System.out.println("Digite 1 para confirmar o livro ou 0 para digitar novamente: ");
		        int confirmOption = in.nextInt();
		        in.nextLine();

		        if (confirmOption == 1) {
		            List<Book> selectedBook = bookDAO.selectBooksByISBN(isbn);

		            if (!selectedBook.isEmpty()) {
		                listBooks.add(selectedBook.get(0));
		                System.out.println("Livro adicionado a devolucao: " + selectedBook.get(0).getTitle());
		            } else {
		                System.out.println("Livro não encontrado. Por favor, insira um ISBN válido.");
		            }
		        }

		        System.out.println("Digite 0 para adicionar outro livro ou digite 1 para ir para o próximo passo: ");
		        option = in.nextInt();
		        in.nextLine();
		    } while (option != 1);
			List<BooksBorrowed> booksNotReturned = new ArrayList<>(loan.getListBooks());
			for (Book returnedBook : listBooks) {
			    booksNotReturned.removeIf(book -> book.getIsbn().equals(returnedBook.getIsbn()));
			}
			if(booksNotReturned.size() == loan.getListBooks().size()) {
				System.out.println("Nenhum livro emprestado foi devolvido");
				return;
			}else {
				loanDAO.returBook(loan, listBooks, LocalDate.now());
			}
		}
		
		public void menuRelatorios(Scanner in) {
			int option = -1;
		    do {
		        System.out.println("Digite: \n\t"
		        		+ "1 livros por genero\n\t"
		        		+ "2 livros mais retirados\n\t"
		        		+ "3 livros fora de estoque\n\t"
		        		+ "4 livros publicados em um intervalo de tempo\n\t"
		        		+ "5 Editoras com mais titulos\n\t"
		        		+ "6 Livros de um autor especifico\n\t"
		        		+ "7 alunos com debitos\n\t"
		        		+ "8 alunos com o maximo de livros emprestados\n\t"
		                + "0 para sair\n");

		        if (in.hasNextInt()) {
		            option = in.nextInt();

		            in.nextLine();
		        } else {
		            in.nextLine();
		            System.out.println("Digite um número válido!");
		            continue;
		        }

		        switch (option) {
		            case 1:
		            	relatorioLivrosGenero();
		                break;
		            case 2:
		            	relatorioLivrosRetirados();
		                break;
		            case 3:
		            	relatorioLivroForaEstoque();
		                break;
		            case 4:
		            	relatorioLivrosPublicadosIntervaloTempo(in);
		                break;
		            case 5:
		            	relatorioEditoraComMaisTitulos();
		            	break;
		            case 6:
		            	relatorioLivrosDeAuthorEspecifico(in);
		            	break;
		            case 7:
		            	EstudantesComDividas();
		            	break;
		            case 8:
		            	AlunosComMaximoLivrosEmprestados();
		            	break;
		            	
		        }
		    } while (option != 0);
		}
		
		public void relatorioLivrosGenero () {
			BookDAO bookDAO = new BookDAO();
			List<Book> bookList = bookDAO.selectAllBooks();
			for (Book book: bookList) {
				System.out.println("id: "+book.getId());
				System.out.println("isbn: "+book.getIsbn());
				System.out.println("titulo: "+book.getTitle());
				System.out.println("editora: "+book.getPublisher());
				System.out.println("quantidade: "+book.getQuantity());
				System.out.println("ano de publicacao: "+book.getYearPublication());
				System.out.println("generos: ");
				for (String string: book.getGenre()) {
					System.out.println("	"+string);
				}
				System.out.println("Autores: ");
				for (String string: book.getAuthor()) {
					System.out.println("	"+string);
				}
			}
		}
		public void relatorioLivrosRetirados() {
			BookDAO bookDAO = new BookDAO();
			List<Book> bookList = bookDAO.selectBooksTimesBorrowed();
			for (Book book: bookList) {
				System.out.println("id: "+book.getId());
				System.out.println("isbn: "+book.getIsbn());
				System.out.println("titulo: "+book.getTitle());
				System.out.println("editora: "+book.getPublisher());
				System.out.println("quantidade: "+book.getQuantity());
				System.out.println("ano de publicacao: "+book.getYearPublication());
				System.out.println("generos: ");
				for (String string: book.getGenre()) {
					System.out.println("	"+string);
				}
				System.out.println("Autores: ");
				for (String string: book.getAuthor()) {
					System.out.println("	"+string);
				}
				System.out.println("veses emprestado: "+book.getTimesBorrowed());
			}
		}
		public void relatorioLivroForaEstoque() {
			BookDAO bookDAO = new BookDAO();
			List<Book> bookList = bookDAO.selectBooksOutStockBooks();
			for (Book book: bookList) {
				System.out.println("id: "+book.getId());
				System.out.println("isbn: "+book.getIsbn());
				System.out.println("titulo: "+book.getTitle());
				System.out.println("editora: "+book.getPublisher());
				System.out.println("quantidade: "+book.getQuantity());
				System.out.println("ano de publicacao: "+book.getYearPublication());
				System.out.println("generos: ");
				for (String string: book.getGenre()) {
					System.out.println("	"+string);
				}
				System.out.println("Autores: ");
				for (String string: book.getAuthor()) {
					System.out.println("	"+string);
				}
			}
		}
		public void relatorioLivrosPublicadosIntervaloTempo(Scanner in) {
			in.nextLine();
	        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	        System.out.print("Digite a data inicial (formato yyyy-MM-dd): ");
	        String dataInicialStr = in.next();

	        System.out.print("Digite a data final (formato yyyy-MM-dd): ");
	        String dataFinalStr = in.next();
	        LocalDate dataInit = null;
	        LocalDate dataEnd = null;
	        try {
	        	dataInit = LocalDate.parse(dataInicialStr, dateFormatter);
	        	dataEnd = LocalDate.parse(dataFinalStr, dateFormatter);

	        } catch (Exception e) {
	            System.out.println("Formato de data inválido. Certifique-se de usar o formato yyyy-MM-dd.");
	            e.printStackTrace();
	        }
	        
	        BookDAO bookDAO = new BookDAO();
			List<Book> bookList = bookDAO.selectBooksPublishedPeriodTime(dataInit,dataEnd);
			for (Book book: bookList) {
				System.out.println("id: "+book.getId());
				System.out.println("isbn: "+book.getIsbn());
				System.out.println("titulo: "+book.getTitle());
				System.out.println("editora: "+book.getPublisher());
				System.out.println("quantidade: "+book.getQuantity());
				System.out.println("ano de publicacao: "+book.getYearPublication());
				System.out.println("generos: ");
				for (String string: book.getGenre()) {
					System.out.println("	"+string);
				}
				System.out.println("Autores: ");
				for (String string: book.getAuthor()) {
					System.out.println("	"+string);
				}
			}
		}
		public void relatorioEditoraComMaisTitulos() {
			PublisherDAO publisherDAO = new PublisherDAO();
			List<Publisher> publisherList = publisherDAO.selectPublisherByCountTitles();
			for (Publisher publisher : publisherList) {
	            System.out.println("ID: " + publisher.getId() + ", Nome: " + publisher.getName());
	        }
		}
		public void relatorioLivrosDeAuthorEspecifico(Scanner in) {
	        in.nextLine();
	        int option = -1;
	        String author = null;

	        do {
	            System.out.println("Digite o nome do autor que deseja pesquisar:");
	            author = in.nextLine().trim();

	            System.out.println("Se deseja digitar novamente, digite 1; digite 0 para sair:");
	            if (in.hasNextInt()) {
	                option = in.nextInt();
	                in.nextLine();
	            } else {
	                in.nextLine();
	                System.out.println("Digite um número válido!");
	                continue;
	            }

	            if (option == 1) {
	                if (!author.isEmpty()) {
	                    break;
	                } else {
	                    System.out.println("Nome do autor não pode estar vazio.");
	                }
	            }

	        } while (option != 0);

	        if (author != null) {
	            BookDAO bookDAO = new BookDAO();
	            List<Book> bookList = bookDAO.selectBooksSpecificAuthor(author);

	            for (Book book : bookList) {
	                System.out.println("id: " + book.getId());
	                System.out.println("isbn: " + book.getIsbn());
	                System.out.println("titulo: " + book.getTitle());
	                System.out.println("editora: " + book.getPublisher());
	                System.out.println("quantidade: " + book.getQuantity());
	                System.out.println("ano de publicacao: " + book.getYearPublication());
	                System.out.println("generos: ");
	                for (String genre : book.getGenre()) {
	                    System.out.println("    " + genre);
	                }
	                System.out.println("Autores: ");
	                for (String authorName : book.getAuthor()) {
	                    System.out.println("    " + authorName);
	                }
	            }
	        } else {
	            System.out.println("Operação cancelada. Autor não fornecido.");
	        }
	    }
		public void EstudantesComDividas() {
			StudentDAO studentDAO = new StudentDAO();
			List<Student> listStudent = studentDAO.selectStudentsDebt();
			for (Student student: listStudent) {
				System.out.println("id: "+student.getId());
				System.out.println("nome: "+student.getName());
				System.out.println("numero de matricula: "+student.getNumberRegistration());
				System.out.println("debitos: "+student.getDebits());
			}
		}
		public void AlunosComMaximoLivrosEmprestados() {
			StudentDAO studentDAO = new StudentDAO();
			List<Student> listStudent = studentDAO.selectStudentsMaximumNumberBooksBorrowed();
			for (Student student: listStudent) {
				System.out.println("id: "+student.getId());
				System.out.println("nome: "+student.getName());
				System.out.println("numero de matricula: "+student.getNumberRegistration());
				System.out.println("debitos: "+student.getDebits());
			}
		}
	}
