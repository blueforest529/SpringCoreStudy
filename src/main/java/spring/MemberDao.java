package spring;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MemberDao {

    private static long nextId = 0;
    private Map<String, Member> map = new HashMap<>();

    public MemberDao(Map<String, Member> map) {
        this.map = map;
    }

    public MemberDao() {

    }

    public Member selectByEmail(String email) {
        return map.get(email);
    }

    public void insert(Member member) {
        member.setId(++nextId);
        map.put(member.getEmail(), member);
        System.out.println("insert");

//        Set<String> keySet = map.keySet();
//        for (String key : keySet) {
////            System.out.println(key + " : " + map.get(key));
//            Member data = map.get(key);
//            System.out.println(data.getAll());
////            System.out.println(data.getName());
////            System.out.println(data.getEmail());
////            System.out.println(data.getPassword());
////            System.out.println(data.getId());
//        }
    }

    public void update(Member member) {
        map.put(member.getEmail(), member);
        System.out.println("update");
//        System.out.println(map);
    }

    public Collection<Member> selectAll() {
        return map.values();
    }
}
