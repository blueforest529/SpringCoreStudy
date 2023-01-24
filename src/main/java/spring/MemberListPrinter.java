package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("listPrinter")
public class MemberListPrinter {
    private MemberDBDao memberDao;
    private MemberPrinter printer;

    public MemberListPrinter(MemberDBDao memberDao, MemberPrinter printer) {
        this.memberDao = memberDao;
        this.printer = printer;
    }

    @Autowired
    public void setMemberDao(MemberDBDao memberDao) {
        this.memberDao = memberDao;
    }

    @Autowired
    public void setMemberPrinter(MemberPrinter printer) {
        this.printer = printer;
    }

    public void printAll() {
        Collection<Member> members = memberDao.selectAll();
        members.forEach(m->printer.print(m));
    }
}
