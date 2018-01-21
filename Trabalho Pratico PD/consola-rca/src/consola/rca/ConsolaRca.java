/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consola.rca;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import logica.AdministradorRemote;

/**
 *
 * @author hddlucas
 */
public class ConsolaRca {

    /**
     * @param args the command line arguments
     */
    static AdministradorRemote consola;

    static Scanner sc = new Scanner(System.in);

    public static void obtemReferencias() {

        InitialContext ctx = null;
        Properties prop = new Properties();

        prop.setProperty("java.naming.factory.initial",
                "com.sun.enterprise.naming.SerialInitContextFactory");

        prop.setProperty("java.naming.factory.url.pkgs",
                "com.sun.enterprise.naming");

        prop.setProperty("java.naming.factory.state",
                "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

        prop.setProperty("org.omg.CORBA.ORBInitialHost", "192.168.56.175");

        prop.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

        try {

            ctx = new InitialContext(prop);

        } catch (NamingException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("InitialContext done");
        String advremote
                = "java:global/consola-ea/consola-ea-ejb/Administrador!logica.AdministradorRemote";

        try {
            System.out.println("start lookup");
            Object x = ctx.lookup(advremote);
            consola = (AdministradorRemote) x;
        } catch (NamingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();  // melhor -> mandar para log
            System.exit(1);
        }

        System.out.println("JNDI lookup done");
    }

    public static int obtemOpcaoMenu(int max) {
        int opcao;
        String texto;
        while (true) {
            try {
                System.out.print("opcao -> ");
                texto = sc.nextLine();
                opcao = Integer.parseInt(texto);
                if ((opcao >= -1) && (opcao <= max)) {
                    return opcao;
                }
                System.out.println("Opcao errada. Ver melhor as instrucoes");
            } catch (NumberFormatException e) {
                System.out.println(" Problema no teclado");
            }

        }
    }

    public static void usersList() {

        System.out.println("\n Lista de Utilizadores: \n");
        List<String> utilizadores = consola.getUsersList();

        if ((utilizadores == null) || (utilizadores.isEmpty())) {
            System.out.println("Nenhum Utilizador Encontrado");
        } else {
            for (String u : utilizadores) {
                System.out.println(u);
            }
        }
    }

    public static void getListOfNonActiveUsers() {

        System.out.println("\nLista de Utilizadores por ativar: \n");
        List<String> utilizadores = consola.getListOfNonActiveUsers();

        if ((utilizadores == null) || (utilizadores.isEmpty())) {
            System.out.println("Nenhum Utilizador por ativar Encontrado");
        } else {
            for (String u : utilizadores) {
                System.out.println(u);
            }
        }

    }

    public static void addNewUser() {

        JsonObjectBuilder userFields = Json.createObjectBuilder();
        String texto;
        boolean pwValida = false;
        boolean usernameValida = false;
        boolean biValida = false;
        boolean nifValida = false;
        boolean nomeValida = false;
        Long nif = null;
        System.out.println("\nAdicionar Novo Utilizador \n");

        //username
        do {
            System.out.print("\nUsername do utilizador(min 4 caracteres) -> ");
            texto = sc.nextLine();

            if (consola.isUserUsernameInDB(texto)) {
                System.out.print("O Username introduzido já existe\n");
            }
            if (texto.length() < 4) {
                System.out.print("O Username deve ter no minimo 4 caracteres\n");
                usernameValida = false;
            } else {
                usernameValida = true;
                userFields.add("username", texto);
            }

        } while (consola.isUserUsernameInDB(texto) || !usernameValida);

        //password
        do {
            System.out.print("\nPassword do utilizador(4-10 caracteres) -> ");
            texto = sc.nextLine();
            if (texto.length() >= 4 && texto.length() <= 10) {
                pwValida = true;
                userFields.add("password", texto);

            } else {
                System.out.print("Password deve ter 4 a 10 caracteres)");
            }
        } while (!pwValida);

        //nome
        do {
            System.out.print("\nNome do utilizador(4-20 caracteres) -> ");
            texto = sc.nextLine();
            if (texto.length() >= 4 && texto.length() <= 20) {
                nomeValida = true;
                userFields.add("nome", texto);
            } else {
                System.out.print("Nome deve ter 4 a 20 caracteres)");
            }
        } while (!nomeValida);

        //bi
        do {
            System.out.print("\nBI do utilizador(min 9 caracteres) -> ");
            texto = sc.nextLine();
            if (consola.isUserBiInDB(texto)) {
                System.out.print("O BI introduzido já existe");
            }
            if (texto.length() >= 9) {
                biValida = true;
                userFields.add("bi", texto);

            } else {
                biValida = false;
                System.out.print("O Bi deve ter no minimo 9 caracteres)");
            }

        } while (consola.isUserBiInDB(texto) || !biValida);

        //nif
        do {
            System.out.print("\nNif do utilizador (9 digitos) -> ");
            texto = sc.nextLine();

            if (texto.length() != 9 || Pattern.matches("[a-zA-Z]+", texto) == true) {
                System.out.print("O Nif deve conter 9 digitos");
                nifValida = false;
            } else {
                nifValida = true;
                userFields.add("nif", texto);
            }

            if (consola.isUserNifInDB(texto)) {
                System.out.print("O Nif introduzido já existe");
            }
        } while (consola.isUserNifInDB(texto) || !nifValida);
        JsonObject fieldsObject = userFields.build();

        if (consola.addNewUser(fieldsObject.toString())) {
            System.out.println("O utilizador foi adicionado com sucesso");
        } else {
            System.out.println("Ocorreu um erro ao adicionar o utilizador");
        }

    }

    public static void deleteUser() {
        int userId;
        System.out.print("\nId do utilizador(0 para cancelar) -> ");
        userId = sc.nextInt();
        sc.skip("\n");

        if (!checkIfUserIdexists(userId) || userId == 0) {
            return;
        }

        if (consola.deleteUser(userId)) {
            System.out.println("Utilizador com o id " + userId + " eliminado com sucesso");
        } else {
            System.out.println("Ocorreu um erro ao eliminar o utilizador");
        }
    }

    public static boolean loginUser() {
        String texto;
        boolean logged = false;
        String username;

        System.out.println("\nLogin \n");
        System.out.println("Introduza as suas credenciais");

        do {
            System.out.print("\nUsername-> ");
            texto = sc.nextLine();

            if (!consola.isUserUsernameInDB(texto)) {
                System.out.print("O Username introduzido nao existe\n");
            }

        } while (!consola.isUserUsernameInDB(texto));
        username = texto;
        do {
            System.out.print("\nPassword-> ");
            texto = sc.nextLine();
            if (!consola.login(username, texto)) {
                System.out.print("Password invalida\n");
            }
        } while (!consola.login(username, texto));

        int userId = consola.getUserIdByUsername(username);

        //check if user is admin
        if (!consola.hasRole(userId, "admin")) {
            System.out.println("\nNao tem permissoes para aceder a esta aplicacao");
            consola();
        }

        return true;

    }

    public static void getUserRolesList() {
        int userId;
        System.out.print("\nId do utilizador -> ");
        userId = sc.nextInt();
        sc.skip("\n");

        if (!checkIfUserIdexists(userId)) {
            return;
        }
        List<String> perfis = consola.getUserRolesList(userId);
        if ((perfis == null) || (perfis.isEmpty())) {
            System.out.println("\nO utilizador nao tem nenhum perfil associado");
        } else {
            System.out.print("\nLista de Perfis do Utilizador\n");
            for (String p : perfis) {
                System.out.println(p);
            }
        }
    }

    public static void addRoleToUser() {
        int userId;
        int roleId;

        System.out.print("\nId do utilizador -> ");
        userId = sc.nextInt();
        sc.skip("\n");
        if (!checkIfUserIdexists(userId)) {
            return;
        }
        if (consola.hasRole(userId, "vendedor")) {
            System.out.println("O utilizador com peril vendedor nao pode  acumular outros perfis");
        } else {
            System.out.println("1");
            List<String> perfis = consola.getRolesList();
            if ((perfis == null) || (perfis.isEmpty())) {
                System.out.println("Nao existem perfis disponiveis");
            } else {

                do {

                    List<String> userRoles = consola.getUserRolesList(userId);
                    if ((userRoles == null) || (userRoles.isEmpty())) {
                        System.out.println("\nO utilizador ainda nao tem nenhum perfil associado");
                    } else {
                        System.out.print("\nLista de Perfis Atuais do Utilizador\n");
                        for (String p : userRoles) {
                            System.out.println(p);
                        }
                    }

                    System.out.println("\nLista de perfis disponiveis");
                    for (String p : perfis) {
                        System.out.println(p);
                    }

                    System.out.print("\nId do perfil(0 para cancelar) -> ");
                    roleId = sc.nextInt();
                    sc.skip("\n");

                    if (roleId == 0) {
                        return;
                    } else if (roleId < 1 || roleId > 4) {
                        System.out.println("\nIntroduza um id de Perfil valido");
                    } else {
                        String role = consola.getRoleNameById(roleId);
                        if (consola.hasRole(userId, role)) {
                            System.out.println("O utilizador ja possui a role selecionada");
                        } else {
                            if (consola.addUserRole(userId, roleId)) {
                                System.out.println("O perfil foi adicionado com sucesso ao utilizador");
                            } else {
                                System.out.println("Ocorrem um erro ao adicionar o perfil ao utilizador");
                            }
                        }
                    }
                } while (roleId < 0 || roleId > 4);

            }

        }

    }

    public static void removeUserRole() {
        int userId;
        int roleId;
        String role="";
        System.out.print("\nId do utilizador -> ");
        userId = sc.nextInt();
        sc.skip("\n");
        if (!checkIfUserIdexists(userId)) {
            return;
        }

        List<String> perfis = consola.getUserRolesList(userId);
        if ((perfis == null) || (perfis.isEmpty())) {
            System.out.println("\nO utilizador nao tem nenhum perfil associado");
        } else {

            do {

                System.out.print("\nLista de Perfis do Utilizador\n");
                for (String p : perfis) {
                    System.out.println(p);
                }

                System.out.print("\nId do perfil(0 para cancelar) -> ");
                roleId = sc.nextInt();
                sc.skip("\n");

                if (roleId == 0) {
                    return;
                } else if (roleId < 1 || roleId > 4) {
                    System.out.println("\nIntroduza um id de Perfil valido");
                } else {
                    role = consola.getRoleNameById(roleId);
                    if (consola.hasRole(userId, role)) {
                        
                        if(consola.removeUserRole(userId,roleId)){
                            System.out.println("O perfil do utilizador foi removido com sucesso");
                            return;

                        }else{
                            System.out.println("Ocorrem um erro ao remover o perfil do utilizador");
                        }
                        
                    } else {
                        System.out.println("O utilizador nao possui o perfil selecionado");
                    }
                }
            } while (roleId < 0 || roleId > 4 || !consola.hasRole(userId,role));
        }

    }

    public static void updateUserInformation() {
        JsonObjectBuilder userFields = Json.createObjectBuilder();
        String texto;
        int id;
        System.out.print("\nId do utilizador -> ");
        id = sc.nextInt();
        sc.skip("\n");
        if (!checkIfUserIdexists(id)) {
            return;
        }
        System.out.println("\nAtualizar  Informacao do Utilizador \n");

        //pais
        System.out.print("\nPais-> ");
        texto = sc.nextLine();
        userFields.add("pais", texto);

        //cidade
        System.out.print("\nCidade-> ");
        texto = sc.nextLine();
        userFields.add("cidade", texto);

        //codigo postal
        System.out.print("\nCodigo Postal-> ");
        texto = sc.nextLine();
        userFields.add("codigo_postal", texto);

        //codigo postal
        System.out.print("\nContacto-> ");
        texto = sc.nextLine();
        userFields.add("contacto", texto);

        JsonObject fieldsObject = userFields.build();
        if (consola.updateUserInformation(id, fieldsObject.toString())) {
            System.out.println("Informacao do utilizador atualizada com sucesso");
        } else {
            System.out.println("Ocorreu um erro ao atualizar a informacao do utilizador");
        }
    }

    public static void ativeUserById() {
        int id;
        System.out.print("\nId do utilizador -> ");
        id = sc.nextInt();
        sc.skip("\n");

        if (!checkIfUserIdexists(id)) {
            return;
        }
        if (consola.isActive(id)) {
            System.out.println("O utilizador que indicou ja se encontra ativo");
            return;
        }
        if (consola.activeUser(id)) {
            System.out.println("O utilizador foi ativo com sucesso");
        } else {
            System.out.println("Ocorreu um erro ao ativar o utilizador");
        }

    }

    public static boolean checkIfUserIdexists(int userId) {
        if (consola.isUserIdInDB(userId) == false) {
            System.out.println("O utilizador com o id " + userId + " nao existe\n");
            return false;
        }
        return true;
    }

    public static void printMenu() {
        System.out.println("\n-------------------------\n");
        System.out.println("Selecione uma opcao\n");
        System.out.println("1- Listar Utilizadores");
        System.out.println("2- Adicionar Novo Utilizador");
        System.out.println("3- Remover Utilizador por ID");
        System.out.println("4- Atualizar Informacao de Utilizador por ID");
        System.out.println("5- Listar Utilizadores nao Ativos");
        System.out.println("6- Ativar utilizador por ID");
        System.out.println("7- Consultar Perfis de Utilizador por ID");
        System.out.println("8- Adicionar Perfil a Utilizador por ID");
        System.out.println("9- Remover Perfil a Utilizador por ID");

        System.out.println("-----");
        System.out.println("Logout -> 0");
        System.out.println("Sair -> -1");

        System.out.println("\n-------------------------\n");
    }

    public static void consola() {
        boolean logged = false;
        boolean continuar = true;
        int opcao;
        //Login User
        logged = loginUser();
        System.out.println("\nLogin efetuado com sucesso");

        if (logged) {
            while (continuar) {
                printMenu();
                opcao = obtemOpcaoMenu(9);

                switch (opcao) {
                    case 1:
                        usersList();
                        break;

                    case 2:
                        addNewUser();
                        break;

                    case 3:
                        deleteUser();
                        break;

                    case 4:
                        updateUserInformation();
                        break;

                    case 5:
                        getListOfNonActiveUsers();
                        break;

                    case 6:
                        ativeUserById();
                        break;

                    case 7:
                        getUserRolesList();
                        break;

                    case 8:
                        addRoleToUser();
                        break;

                    case 9:
                        removeUserRole();
                        break;

                    case 0:
                        consola();

                    case -1:
                        System.exit(0);

                    default:
                        System.out.println("Isto nao era suposto acontecer");
                        break;
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        obtemReferencias();
        consola();

    }

}
