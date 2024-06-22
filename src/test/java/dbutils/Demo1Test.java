package dbutils;

import org.junit.jupiter.api.Test;
import user.util.dbutils.Demo1;
import user.util.dbutils.Stu;

public class Demo1Test {
    @Test
    public void fun1() {
        Stu stu = new Stu(1,"ZhangSan",111,"male");
//        Demo1.addStu(stu);
//        Demo1.updateStu(stu);
        Demo1.deleteStu(1);
    }
}
