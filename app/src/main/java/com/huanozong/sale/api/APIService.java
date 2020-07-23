package com.huanozong.sale.api;

import com.huanozong.sale.bean.AreaBean;
import com.huanozong.sale.bean.BaseBean;
import com.huanozong.sale.bean.CompanyBean;
import com.huanozong.sale.bean.CompanyDetail;
import com.huanozong.sale.bean.LevelBaseBean;
import com.huanozong.sale.bean.LoginData;
import com.huanozong.sale.bean.OrderListBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIService {


    /**
     * http://app.hzmtkj.com/portal/login/doLogin.html
     * 登陆
     * @param username
     * @param password
     */
    @GET("portal/login/doLogin.html")
    Call<LoginData> doLogin(@Query("username")String username, @Query("password")String password);

    /**
     * http://app.hzmtkj.com/portal/login/getCompany.html
     * 获取绑定客户信息
     * uid 是从登陆获取的
     * @param uid
     */

    @GET("portal/login/getCompany.html")
    Call<CompanyBean> getCompany(@Query("uid")int uid);

    /**
     * http://app.hzmtkj.com/portal/login/getCompany.html
     * 获取审核客户列表
     * uid 是从登陆获取的
     */

    @GET("portal/login/getCompany.html")
    Call<CompanyBean> getCompanyShenhe(@QueryMap HashMap<String,String> map);


    /**
     * app.hzmtkj.com/portal/login/addCompany.html
     * 添加客户信息
     * uid 登陆获取
     * company 客户公司
     * brand 客户品牌
     * address 客户地址
     * user JSON数组：name 联系人  tel 联系电话   sex性别 0女1男 old 年龄  职务 duties
     * type 客户类型 1为重点客户 2为普通客户
     */
    @GET("portal/login/addCompany.html")
    Call<BaseBean> addCompany(@Query("uid")int uid,
                              @Query("company")String company,
                              @Query("brand")String brand,
                              @Query("address")String address,
                              @Query("type") int type,
                              @Query("user") String user);

    //[{"name":"susu","tel":18328594069,"sex":0,"old":15,"duties":"程序员"}]
    @GET("portal/login/getArea.html")
    Call<List<AreaBean>> getArae();


    /**
     * 获取门禁位置
     *
     * @param area  数组形式  [2,6]
     * @param stime long
     * @param etime long
     * @return
     */
    @GET("portal/login/getDoor.html")
    Call<ResponseBody> getDoor(@Query("area") String area, @Query("stime") long stime, @Query("etime") long etime);

    @GET("portal/login/getDoor.html")
    Call<LevelBaseBean> getDoor(@Query("stime") long stime, @Query("etime") long etime);


    /**
     * 提交筛选之后的数据
     * @param com_id 商户id
     * @param id 销售id
     * @param stime 开始时间戳
     * @param etime 结束时间戳
     * @param data  点位json数组  [{"id":1044,"door_a":1,"door_b":1}, {"id":1140,"door_a":1,"door_b":1}]
     * @param status 状态码  0保存，1确认
     * @return
     */
    @POST("/portal/login/setAreamap.html")
    Call<ResponseBody> setAreamap(@Query("com_id")int com_id,
                                  @Query("uid")int id,
                                  @Query("stime")long stime,
                                  @Query("etime") long etime,
                                  @Query("datas")String data,
                                  @Query("status")int status);

    @FormUrlEncoded
    @POST("/portal/login/setAreamap.html")
    Call<ResponseBody> setAreamap(@FieldMap Map<String, String> map);
    /**
     * 获取客户信息接口
     * @param id 客户id
     * @return
     */
    @GET("portal/Customer/getCostomer.html")
    Call<CompanyDetail> getCostomer(@Query("id")int id);


    /**
     * 获取订单列表
     * @param id  销售id
     * @return
     */
    @GET("portal/customer/OrderList.html")
    Call<OrderListBean> OrderList(@Query("uid")int id);


    /**
     * 根据订单id获取点位信息
     * @param id
     * @return
     */
    @GET("portal/customer/editOrder.html")
    Call<LevelBaseBean> editOrder(@Query("id")int id);

    /**
     * 管理审核
     * @return
     */
    @GET("portal/login/shenhe.html")
    Call<BaseBean> shenhe(@Query("id")int id);

    /**
     * 更改客户类型
     * @return
     */
    @GET("portal/login/changeKh.html")
    Call<BaseBean> changeKh(@Query("id")int id,@Query("ty")int ty);


    /**
     * 更改客户类型
     * @return
     */
    @GET("portal/login/changeKW.html")
    Call<BaseBean> changePassword(@Query("uid")int id,@Query("password")String password);
}
