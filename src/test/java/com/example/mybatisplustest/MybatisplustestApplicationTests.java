package com.example.mybatisplustest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplustest.dao.UserMapper;
import com.example.mybatisplustest.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisplustestApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void select(){
        List<User> userList = userMapper.selectList(null);
        for (User u : userList){
            System.out.println("用户名:" + u.getName());
        }
    }

    @Test
    public void insert() throws ParseException {
        User user = new User();
        user.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse("2019-09-09"));
        user.setDeptid(1);
        user.setEmail("hello@163.com");
        user.setGender(0);
        user.setName("小明");
        user.setPassword("123456");
        int row = userMapper.insert(user);
        System.out.println("新增返回受影响的行数为:" + row);
    }

    /**
     * 按主键查询user表用户对象
     */
    @Test
    public void queryUserById(){
        User user = userMapper.selectById(1153150609521197058L);
        System.out.println("用户名:" + user.getName() + "邮箱为:" + user.getEmail());
    }

    /**
     * 将请求参数封装到Map集合作为参数查询用户列表信息
     */
    @Test
    public void selectByMap(){
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("name","小明");
        columnMap.put("email","hello@163.com");
        List<User> users = userMapper.selectByMap(columnMap);
        for (User u: users){
            System.out.println("用户名:" + u.getName()+ "邮箱为" + u.getEmail());
        }
    }

    /**
     * 条件构造器查询0-1
     */
    @Test
    public void selectByWrapper(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.like("name","tom").eq("gender",0);//修改一为数据库中的列名
        //queryWrapper.it("age,40);//这个代码等价于age<40
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user: users){
            System.out.println("用户名:" + user.getName()+"邮箱:" + user.getEmail());
        }
    }

    /**
     * 构造器查询0-2
     */
    @Test
    public void selectByWrapper02(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","tom").between("age",10,20).isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user: users){
            System.out.println("用户名:" + user.getName()+"邮箱:" + user.getEmail());
        }
    }

    /**
     * 构造器查询0-3
     */
    @Test
    public void selectByWrapper03(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","王").or().le("age",25).
                orderByDesc("age").orderByAsc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user: users){
            System.out.println("用户名:" + user.getName()+"邮箱:" + user.getEmail());
        }
    }

    /**
     * 查询出生日期为1998-02-03并且员工的部门编号为1,2的员工信息列表
     */
    @Test
    public void selectByWrapper04(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(birth,'%Y-%m-%d')={0}","2001-06-24").inSql("deptid","1,2");
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user: users){
            System.out.println("用户名:" + user.getName()+"邮箱:" + user.getEmail());
        }
    }

    /**
     *查询年龄大于25的用列表信息
     */
    @Test
    public void selectPage(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age",10);
        Page<User> page = new Page<>(1,2); //参数1,当前页码,参数二 :每页显示多少行数据
        IPage<User> iPage = userMapper.selectPage(page,queryWrapper);
        System.out.println("总页数:" + iPage.getPages());
        System.out.println("总记录数:" + iPage.getTotal());
        List<User> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

    /**
     * 根据主键id更新用户信息
     */
    @Test
    public void updateById() throws ParseException {
        User user = new User();
        user.setId(1153150609521197058L); //修改用户的id
        user.setName("小明明"); //用户修改后
        user.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1992-02-06"));
        user.setDeptid(2);
        user.setEmail("xiaomingming@163.com");
        int row = userMapper.updateById(user);
        System.out.println("新增后返回的受影响的行数为:"+ row);
    }

    /**
     *
     */
    @Test
    public void updateById02(){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name","xiaoliu").eq("age",12);

        User user = new User();
        user.setName("小星星");
        user.setDeptid(1);
        user.setEmail("xiaoxingxing@163.com");
        int row = userMapper.update(user,updateWrapper);
        System.out.println("新增后返回的受影响的行数为:" + row);
    }

    /**
     * 根据id删除数据
     */
    @Test
    public void deleteUser(){
        int row = userMapper.deleteById(1153150609521197058L);
        System.out.println("删除的记录条数为" + row);
    }

    /**
     * 封装到map中
     */
    @Test
    public void deleteByMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","little01");
        map.put("age",12);
        int row = userMapper.deleteByMap(map);
        System.out.println("删除的记录条数为:"+row);
    }

    /**
     * 多个id批量删除
     */
    @Test
    public void deleteBatchids(){
        int row = userMapper.deleteBatchIds(Arrays.asList(1153225866160484354L,1153226070645297153L));
        System.out.println("删除的记录条数为:");
    }

}
