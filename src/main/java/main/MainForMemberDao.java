package main;

import config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainForMemberDao {
    private static AnnotationConfigApplicationContext ctx = null;

    public static void main(String[] args) throws IOException {
        ctx = new AnnotationConfigApplicationContext(AppCtx.class);

//        memberDBDao = ctx.getBean(MemberDBDao.class);

//        selectAll();
//        updateMember();
//        insertMember();


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("명령어를 입력하세요.");
            String command = reader.readLine();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("종료합니다.");
                break;
            }

            if (command.startsWith("new ")) {
                processNewCommand(command.split(" "));
                continue;
            } else if (command.startsWith("change ")) {
                processChangeCommand(command.split(" "));
                continue;
            } else if (command.startsWith("list")) {
                processListCommand();
                continue;
            } else if (command.startsWith("info ")) {
                processInfoCommand(command.split(" "));
                continue;
            }


            printHelp();
        }


        ctx.close();
    }

//    private static void selectAll() {
//        System.out.println("select all ---");
//        int total = memberDBDao.count();
//        System.out.println("전체 데이터 : " + total);
//        List<Member> members = memberDBDao.selectAll();
//
//        for (Member m : members) {
//            System.out.println(m.getId() + " : " + m.getEmail() + " : " + m.getName());
//        }
//    }
//
//    private static void updateMember() {
//        System.out.println("update member ---");
//        Member member = memberDBDao.selectByEmail("cheong@gmail.com");
//        String oldPw = member.getPassword();
//        String newPw = Double.toHexString(Math.random());
//        member.changePassword(oldPw, newPw);
//
//        memberDBDao.update(member);
//        System.out.println("암호 변경   : " +oldPw + " > " + newPw);
//    }
//
//    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");
//
//    private static void insertMember() {
//        System.out.println("insert member ---");
//        String prefix = formatter.format(LocalDateTime.now());
//        Member member = new Member(prefix + "@test.com", prefix, prefix, LocalDateTime.now());
//        memberDBDao.insert(member);
//        System.out.println(member.getId()+"  데이터 추가");
//    }

    private static void processNewCommand(String[] args) {
        if (args.length != 5) {
            printHelp();
            return;
        }

        MemberRegisterService regSvc = ctx.getBean("memberRegSvc", MemberRegisterService.class);
        RegisterRequest req = new RegisterRequest();

        req.setEmail(args[1]);
        req.setName(args[2]);
        req.setPassword(args[3]);
        req.setConfirmPassword(args[4]);

        if (!req.isPasswordEqualToConfirmPassword()) {
            System.out.println("암호와 암호 확인이 일치하지 않습니다. \n");
            return;
        }

        try {
            regSvc.regist(req);
            System.out.println("등록 했습니다. \n");
        } catch (DuplicateMemberException e) {
            System.out.println("이미 존재하는 이메일 입니다. \n");
        }
    }

    private static void processChangeCommand(String [] args) {
        if (args.length != 4) {
            printHelp();
            return;
        }

        ChangePasswordService changePwdSvc = ctx.getBean("changePwdSvc", ChangePasswordService.class);

        try {
            changePwdSvc.changePassword(args[1], args[2], args[3]);
            System.out.println("암호를 변경 했습니다 \n");
        } catch (MemberNotFoundException e) {
            System.out.println("존재하지 않는 이메일 입니다. \n");
        } catch (WrongIdPasswordException e) {
            System.out.println("이메일과 암호가 일치하지 않습니다. \n");
        }

    }

    private static void processListCommand() {
        MemberListPrinter listPrinter = ctx.getBean("listPrinter", MemberListPrinter.class);
        listPrinter.printAll();
    }

    private static void processInfoCommand(String[] args) {
        if (args.length != 2) {
            printHelp();
            return;
        }

        MemberInfoPrinter infoPrinter = ctx.getBean("infoPrinter", MemberInfoPrinter.class);
        infoPrinter.printMemberInfo(args[1]);

    }

    private static void printHelp() {
        System.out.println();
        System.out.println("잘못된 명령어 입니다. 아래 사용법을 확인하세요.");
        System.out.println("명령어 사용법 : ");
        System.out.println("new 이메일 이름 암호 암호확인 ");
        System.out.println("change 이메일 현재비번 변경비번 ");
        System.out.println("list ");
        System.out.println("info 이메일  ");
        System.out.println();
    }

}
