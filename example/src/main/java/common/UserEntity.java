package common;

import lombok.Data;

import java.util.Date;

@Data
public class UserEntity implements Comparable<UserEntity> {
    private String userName;
    private Date regTime;
    private Long buyCount;
    private Integer age;
    private Integer sex;
    private String address;


    @Override
    public int compareTo(UserEntity o) {
        if (o.age.equals(this.age))
            return 0;
        else
            return -1;
    }
}
