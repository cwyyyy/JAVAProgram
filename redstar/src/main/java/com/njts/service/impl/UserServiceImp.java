package com.njts.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.mapper.RoleAuthMapper;
import com.njts.mapper.UserMapper;
import com.njts.mapper.UserRoleMapper;
import com.njts.pojo.PageR;
import com.njts.pojo.RoleAuth;
import com.njts.pojo.User;
import com.njts.pojo.UserRole;
import com.njts.service.RoleAuthService;
import com.njts.service.UserService;
import com.njts.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserServiceImp  implements UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	//注入UserMapper
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleAuthMapper roleAuthMapper;
	@Autowired
	private  UserRoleMapper userRoleMapper;
	@Autowired
	private RoleAuthService roleAuthService;

	//根据用户名查找用户的业务方法
	@Override
	public User findUserByCode(String userCode) {
		LambdaQueryWrapper<User> qw=new LambdaQueryWrapper<>();
		qw.eq(User::getUserCode,userCode);
		return userMapper.selectOne(qw);
	}

	//分页查询用户列表的业务方法
	@Override
	public PageR queryUserPage(Long num, Long size, User user) {
       Page<User> page = new Page<>(num, size);
        QueryWrapper<User> qw =new QueryWrapper<>();
		 qw
				 .like(StringUtils.isNotBlank(user.getUserCode()),"q1.user_code",user.getUserCode())
				 .eq(StringUtils.isNotBlank(user.getUserState()),"q1.user_state",user.getUserState())
				 .eq(StringUtils.isNotBlank(user.getUserType()),"q1.user_type",user.getUserType())
				 .eq("q1.is_delete",0);
        // 创建查询条件
		Page Page = userMapper.selectToPage(page, qw);
		PageR pages =new PageR(num,size,	Page.getTotal(),Page.getPages(),Page.getRecords());
		return pages;
	}

	//添加用户的业务方法
	@Override
	public Result saveUser(User user) {
		//根据用户名查询用户
		LambdaQueryWrapper<User> qw=new LambdaQueryWrapper<User>();
		qw.eq(User::getUserCode,user.getUserCode());
		User oldUser = userMapper.selectOne(qw);
		if(oldUser!=null){//用户已存在
			return Result.err(Result.CODE_ERR_BUSINESS, "该用户已存在！");
		}
		//用户不存在,对密码加密,添加用户
		String userPwd = passwordEncoder.encode(user.getUserPwd());
		user.setUserPwd(userPwd);
		userMapper.insert(user);
		return Result.ok("添加用户成功！");
	}

	//修改用户状态的业务方法
	@Override
	public Result updateUserState(User user) {

		//根据用户id修改用户状态
		LambdaUpdateWrapper<User> qw=new LambdaUpdateWrapper<>();
		qw.set(User::getUserState,user.getUserState());
		qw.eq(User::getUserId,user.getUserId());
		int i = userMapper.update(null,qw);
		if(i>0){
			return Result.ok("修改成功！");
		}
		return Result.err(Result.CODE_ERR_BUSINESS, "修改失败！");
	}

	//根据用户id删除用户的业务方法
	@Override
	public int deleteUserById(Integer userId) {
		//根据用户id修改用户状态为删除状态
		return userMapper.deleteById(userId);
	}

	//修改用户昵称的业务方法
	@Override
	public Result updateUserName(User user) {
		//根据用户id修改用户昵称
		LambdaUpdateWrapper<User> qw=new LambdaUpdateWrapper<>();
		qw.set(User::getUserName,user.getUserName());
		qw.eq(User::getUserId,user.getUserId());
		int i = userMapper.update(null,qw);
		if(i>0){//修改成功
			return Result.ok("用户修改成功！");
		}
		//修改失败
		return Result.err(Result.CODE_ERR_BUSINESS, "用户修改失败！");
	}

	//重置密码的业务方法
	@Override
	public Result resetPwd(Integer userId) {

		//创建User对象并保存用户id和加密后的重置密码123456
		User user = new User();
		user.setUserId(userId);
		user.setUserPwd(passwordEncoder.encode("123456"));

		//根据用户id修改密码
		int i = userMapper.updateById(user);

		if(i>0){//密码修改成功
			return Result.ok("密码重置成功！");
		}
		//密码修改失败
		return Result.err(Result.CODE_ERR_BUSINESS, "密码重置失败！");
	}
   //批量删除用户
	@Override
	public void deleteUsersByIds(List<Integer> ids) {
		userMapper.deleteBatchIds(ids);
	}


   //修改用户权限
	@Transactional
	@Override
	public boolean updateUserAuthByUserId(List<Integer> authIds, Integer userId) {
		//删除用户对应的角色的权限
		//先根据用户id得到用户角色id(有可能不止一个角色)???
		LambdaQueryWrapper<UserRole> qw=new LambdaQueryWrapper<>();
		qw.eq(UserRole::getUserId,userId);
		UserRole userRole = userRoleMapper.selectOne(qw);
		List<UserRole> userRoles = userRoleMapper.selectList(qw);
		LambdaQueryWrapper<RoleAuth> qw1=new LambdaQueryWrapper<>();

		qw1.eq(RoleAuth::getRoleId,userRole.getRoleId());
		roleAuthMapper.delete(qw1);
	//重新添加用户的角色的权限id
		//创建实例列表
	    List<RoleAuth>  AuthList= authIds.stream().map(res->new RoleAuth(null,userRole.getRoleId(),res)).collect(Collectors.toList());
	return  roleAuthService.saveBatch(AuthList);
	}

	@Override
	public void outFile(List records, HttpServletResponse response) {

	}

	//导出数据为excel
//	@Override
//	public void outFile(List records, HttpServletResponse resp) {
//    //它可以获取activitys数据，生成一个Excel文件,（也可以揣着这些数据直接输出，如写到浏览器）
//        // workbook执行写功能
//    HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("用户列表");
//        HSSFRow row = sheet.createRow(0);
//        HSSFCell cell=null;
//        cell = row.createCell(0);//单元格
//        cell.setCellValue("用户名");
//        cell = row.createCell(1);//单元格
//        cell.setCellValue("昵称");
//        cell = row.createCell(2);//单元格
//        cell.setCellValue("用户状态");
//        cell = row.createCell(3);//单元格
//        cell.setCellValue("创建人");
//        cell = row.createCell(4);//单元格
//        cell.setCellValue("创建时间");
//        Activity activity=null;
//        HSSFRow row1=null;
//        HSSFCell cell1=null;
//        if (activitys !=null && activitys.size()>0) {
//
//            for (int i = 0; i < activitys.size(); i++) {
//                //写入数据
//                activity = activitys.get(i);
//                row1 = sheet.createRow(i + 1);//第一行
//                cell1 = row1.createCell(0);//第一个单元格
//                cell1.setCellValue(activity.getName());
//                cell1 = row1.createCell(1);//第二个单元格
//                cell1.setCellValue(activity.getUser().getName());
//                cell1 = row1.createCell(2);//。。。。。。。。。
//                cell1.setCellValue(activity.getStartDate());
//                cell1 = row1.createCell(3);
//                cell1.setCellValue(activity.getEndDate());
//                cell1 = row1.createCell(4);
//                cell1.setCellValue(activity.getCost());
//                cell1 = row1.createCell(5);
//                cell1.setCellValue(activity.getDescription());
//                cell1 = row1.createCell(6);
//                cell1.setCellValue(activity.getCreateTime());
//            }}
//
////        //输出流 写文件，生成文件
////        FileOutputStream outputStream = new FileOutputStream("D:\\CRM\\CRM2023\\CRM01\\src\\main\\resources\\fails\\abc.xls");
////        workbook.write(outputStream);
//        //关闭资源
////        outputStream.close();
////        workbook.close();
//
//     //生成的文件下载到浏览器
//        //设置响应类型
//        resp.setContentType("application/octet-stream;charset=UTF-8");
////        BufferedInputStream is=null;
////        //读（从哪里读到内存）加一个缓冲管道
////        try {
////            is = new BufferedInputStream(new FileInputStream("D:\\CRM\\CRM2023\\CRM01\\src\\main\\resources\\fails\\abc.xls"));
//        //获取输出流，写完直接发客户端
//        OutputStream out=resp.getOutputStream();
//
//        workbook.write(out);
//
//        resp.addHeader("Content-Disposition","attachment;filename=myTest.xls");//响应头信息
//            //读的是字节流,设置缓冲区，不要一个一个读，太慢
////            byte[] buff=new byte[256];
////            int len=0;
////            while ((len=is.read(buff))!=-1){
////                //写(复制)
////                out.write(buff,0,len);
////            }
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        } finally {
////            //不是自己new的不用关，out不是自己new的不用关
////            is.close();
//            workbook.close();
//            out.flush();
////        }
//    }
//
//
//
//	}
}
