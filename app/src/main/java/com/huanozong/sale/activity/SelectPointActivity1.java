package com.huanozong.sale.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanozong.sale.R;
import com.huanozong.sale.adapter.AreaAdapter;
import com.huanozong.sale.adapter.CommunityAdapter;
import com.huanozong.sale.adapter.SugAdapter;
import com.huanozong.sale.api.HttpService;
import com.huanozong.sale.bean.AreaBean;
import com.huanozong.sale.bean.Level0Edit;
import com.huanozong.sale.bean.Level0Item;
import com.huanozong.sale.bean.Level1Item;
import com.huanozong.sale.bean.Level2Item;
import com.huanozong.sale.bean.LevelBaseBean;
import com.huanozong.sale.location.MyBaiduLocation;
import com.huanozong.sale.location.OnMapLocation;
import com.huanozong.sale.util.AreaUtil;
import com.huanozong.sale.util.MapUtil;
import com.huanozong.sale.util.SharedPreferencesUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectPointActivity1 extends BaseActivity implements SugAdapter.OnItemClickListen,CommunityAdapter.CheckInterface,CommunityAdapter.CheckBoxClick, OnMapLocation {

    private boolean isNumberList = false;
    private LocationClient mLocationClient;
    private MyBaiduLocation mLoclistener;
    //开始进入页面获取点位所有数据,并在地图上显示
    //根据3个需求筛查需要的点位     定位范围    区域    数量
    //选择了数量之后不能再选择定位范围和区域
    //区域和定位范围分属2个list，取2个列表的交集为选中列表，从选中列表里面选择数量
    private Map<Integer, Level0Item> levelMap;
    private Map<Integer, Level0Edit> editMap;
    private List<Level1Item> communityList;

    //用来放置地图上的点位
    private Map<Level2Item, Marker> markerMap;

    private int aNum = 0;
    private int bNum = 0;
    private int doorNum = 0 ;
    private int communityNum = 0;

    private LatLng latLng;

    private MapView mapView;
    private BaiduMap baiduMap;
    private long stime;
    private long etime;
    private int companyId;

    //    标记地图上的颜色
    private BitmapDescriptor bitmapRed;
    private BitmapDescriptor bitmapGreen;
    private BitmapDescriptor bitmapFlag;

    private Button btChooseNumber;
    private TextView tvDoorInfo;

    private Button btSearch;
    private EditText etSearch;
    private RecyclerView rvSearch;

    private Button btDistance;
    private EditText etDistance;

    private AlertDialog dialogNumber;
    private Button btNumber;
    private EditText etNumber;

    private AlertDialog dialogAB;
    private Button bt_a;
    private Button bt_b;
    private Button bt_dh;

    private RecyclerView rvCommunity;

    private ImageButton leftBack;
    private TextView common_title;

    private FrameLayout frameLayout;
    private RecyclerView rv_area;
    private AreaAdapter areaAdapter;
    private List<AreaBean> areaList;

    private TextView btSubmit;
    private TextView btCancel;
    private int order_id;

    //3个筛选条件
    private AlertDialog dialogPoint,dialogDistance;
    private SuggestionSearch suggestionSearch;
    private SugAdapter sugAdapter;
    private OnGetSuggestionResultListener listener;
    private List<SuggestionResult.SuggestionInfo> listsug;

    //两个预选列表
    private List<MultiItemEntity> areaCommunityList;
    private List<MultiItemEntity> locaCommunityList;

    //选中列表
    private List<MultiItemEntity> selectCommunityList;

    private boolean isEdit = false;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        companyId = intent.getIntExtra("company_id",-1);
        stime = intent.getLongExtra("stime",-1);
        etime = intent.getLongExtra("etime",-1);

        order_id = intent.getIntExtra("order_id",-1);

        if(order_id == -1){
            //获取所有点位数据
            getAllData(stime,etime);
        }else {
            //获取编辑信息数据
            isEdit = true;
            getEditOrder();
        }

        initView();

        initData();

        //获取当前位置
        myPermission();

        setMakerStatus();

        setSuggestion();
    }

    private void getEditOrder() {
        HttpService.getService().editOrder(order_id)
                .enqueue(new Callback<LevelBaseBean>() {
                    @Override
                    public void onResponse(Call<LevelBaseBean> call, Response<LevelBaseBean> response) {
                        if (response.body()!=null){
                            dealData(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<LevelBaseBean> call, Throwable t) {
                        Toast.makeText(SelectPointActivity1.this,"无法获取点位数据，请检查网络数据重新进入此页面",Toast.LENGTH_SHORT).show();
                        Log.e("tag","onFailue "+ t.getMessage());
                    }
                });
    }

    private void getLocation() {
        baiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (rvCommunity.getVisibility()==View.VISIBLE){
                    rvCommunity.setVisibility(View.GONE);
                }else {
                    rvCommunity.setVisibility(View.VISIBLE);
                }

            }
        });

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {

                Bundle bundle = marker.getExtraInfo();
                if (bundle==null){
                    return true;
                }
                final Level2Item door = (Level2Item) bundle.get("door");
                dialogAB.setTitle(door.getCommunityName()+"\n"+door.getName());
                dialogAB.show();
                //bt需要显示三种颜色 1、灰色不可选 2、绿色已经选中 3、红色非选中

                Log.e("tag","a:"+door.isaValid()+"b:"+door.isbValid());
                if (door.isaValid()){
                    bt_a.setBackgroundResource(R.drawable.select_door_ab);
                    bt_a.setSelected(door.isSelectDoorA());
                }else {
                    bt_a.setBackgroundResource(R.color.gray);
                }

                if(door.isbValid()){
                    bt_b.setBackgroundResource(R.drawable.select_door_ab);
                    bt_b.setSelected(door.isSelectDoorB());
                }else {
                    bt_b.setBackgroundResource(R.color.gray);
                }

//                bt_a.setSelected(door.isSelectDoorA());
//                bt_b.setSelected(door.isSelectDoorB());

                bt_a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("tag","onclick a:"+door.isaValid()+"b:"+door.isbValid());
                        if (!door.isaValid()){
                            Toast.makeText(SelectPointActivity1.this,"该点已被其他广告占用",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        door.setSelectDoorA(!door.isSelectDoorA());
                        //获取小区id，根据小区id找到小区，添加到左边列表,刷新地图
                        dialogAB.dismiss();
                        addDoorToLeftList(door);
                    }
                });
                bt_b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Log.e("tag","a:"+door.isaValid()+"b:"+door.isbValid());
                        if (!door.isbValid()){
                            Toast.makeText(SelectPointActivity1.this,"该点已被其他广告占用",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        door.setSelectDoorB(!door.isSelectDoorB());
                        dialogAB.dismiss();
                        addDoorToLeftList(door);
                    }
                });

                bt_dh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double[] doubles = MapUtil.StringToLocation(door.getLocation());
                        if (doubles==null||bdLocation==null){
                            return;
                        }


                        //开始导航  若app上装有百度地图
                        if (MapUtil.isBaiduMapInstalled()){
                            MapUtil.openBaiDuNavi(SelectPointActivity1.this,
                                    bdLocation.getLongitude(),bdLocation.getLatitude(),"我的位置",
                                    doubles[1],doubles[0],door.getCommunityName()+" "+door.getName());
                        }else {
                            Toast.makeText(SelectPointActivity1.this,"请下载百度地图",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                return true;
            }
        });

        //开启定位图层，设置定位数据
        baiduMap.setMyLocationEnabled(true);
        //禁止旋转地图
        baiduMap.getUiSettings().setOverlookingGesturesEnabled(false);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("BD09LL");
        option.setOpenGps(true); // 打开gps
        option.setScanSpan(1000*10);

        mLocationClient = new LocationClient(this);
        baiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,true,null));
        mLocationClient.setLocOption(option);
        mLoclistener = new MyBaiduLocation(baiduMap);
        mLoclistener.setOnLocation(this);
        mLocationClient.registerLocationListener(mLoclistener);
        mLocationClient.start();
    }
    private String[] mPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

    private void myPermission() {

        RxPermissions permission = new RxPermissions(this);
        permission.requestEach(mPermissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        Log.i("tag", "accept: " + permission.toString());
                        if (permission.granted) {
                            //权限获取成功
                            //第一次进入该app，定位当前位置
                            getLocation();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //权限获取失败，但是没有永久拒绝
                        } else {
                            //权限获取失败，而且被永久拒绝
                        }
                    }
                });
    }

    private void addDoorToLeftList(Level2Item door) {
        if (communityAdapter==null){
            Toast.makeText(this,"请先选择区域或者定位，再选择数量",Toast.LENGTH_SHORT).show();
            return;
        }

        //用有全部参数的list找,找到之后跳出循环，刷新左边列表，刷新地图
        for (Level1Item community : communityList) {
            if (community.getId() == door.getC_id()){
                if (isNumberList){
                    if (!numberList.contains(community)){
                        numberList.add(community);
                    }
                    computeNumberToNumber();
                    break;
                }else {
                    if (!selectCommunityList.contains(community)){
                        selectCommunityList.add(community);
                    }
                    computeNumber();
                    break;
                }
            }
        }

        markerMap.get(door).setIcon(door.isSelect()?bitmapGreen:bitmapRed);
        communityAdapter.notifyDataSetChanged();

    }

    //设置maker的状态  红色
    private void setMakerStatus(){
        bitmapRed = BitmapDescriptorFactory
                .fromResource(R.mipmap.location_red);
        bitmapGreen = BitmapDescriptorFactory
                .fromResource(R.mipmap.location_green);
        bitmapFlag = BitmapDescriptorFactory
                .fromResource(R.mipmap.flag_hs);
    }

    private void initData() {
        levelMap = new HashMap<>();
        editMap = new HashMap<>();
        markerMap = new HashMap<>();
        communityList = new ArrayList<>();

        areaCommunityList = new ArrayList<>();
        locaCommunityList = new ArrayList<>();
        selectCommunityList = new ArrayList<>();
    }

    private void initView() {

        frameLayout = findViewById(R.id.fl_content);
        rv_area = findViewById(R.id.rv_area);
        rv_area.setLayoutManager(new LinearLayoutManager(this));
        areaList = AreaUtil.getList(this);
        areaAdapter = new AreaAdapter(this,areaList);
        rv_area.setAdapter(areaAdapter);

        common_title = findViewById(R.id.common_title);
        common_title.setText("选择点位");
        leftBack = findViewById(R.id.leftBack);
        leftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mapView = findViewById(R.id.map_view);
        baiduMap = mapView.getMap();

        btChooseNumber = findViewById(R.id.bt_number);

        tvDoorInfo = findViewById(R.id.tv_show_info);

        dialogPoint = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edittext,null,false);
        dialogPoint.setTitle("请输入点位名称");
        dialogPoint.setView(view);
        btSearch = view.findViewById(R.id.bt_search);
        etSearch = view.findViewById(R.id.et_search);
        rvSearch= view.findViewById(R.id.rv_search);
        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        listsug = new ArrayList<>();
        sugAdapter = new SugAdapter(SelectPointActivity1.this,listsug);
        rvSearch.setAdapter(sugAdapter);

        dialogDistance = new AlertDialog.Builder(this).create();
        View distanceView = LayoutInflater.from(this).inflate(R.layout.dialog_radio,null,false);
        dialogDistance.setTitle("请输入点位范围");
        dialogDistance.setView(distanceView);
        etDistance = distanceView.findViewById(R.id.et_radio);
        btDistance = distanceView.findViewById(R.id.bt_radio_submit);

        dialogNumber = new AlertDialog.Builder(this).create();
        View numView = LayoutInflater.from(this).inflate(R.layout.dialog_edittext_number,null,false);
        dialogNumber.setTitle("请输入需要点位个数");
        dialogNumber.setView(numView);
        etNumber = numView.findViewById(R.id.et_number);
        btNumber = numView.findViewById(R.id.bt_submit);

        dialogAB = new AlertDialog.Builder(this).create();
        View abView = LayoutInflater.from(this).inflate(R.layout.dialog_select_ab,null,false);
        dialogAB.setView(abView);
        bt_a = abView.findViewById(R.id.bt_a);
        bt_b = abView.findViewById(R.id.bt_b);
        bt_dh = abView.findViewById(R.id.bt_dh);

        rvCommunity = findViewById(R.id.rv_door);
        rvCommunity.setLayoutManager(new LinearLayoutManager(this));
        rvCommunity.addItemDecoration(new DividerItemDecoration(this,1));

        selectArea();
    }

    private void selectArea() {
        btSubmit = findViewById(R.id.tv_area_submit);
        btCancel = findViewById(R.id.tv_area_cancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.setVisibility(View.GONE);
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.setVisibility(View.GONE);
                //处理第一次数据把所有数据变成非选中，地图上变成红色
                setPointFalse();
                //提交数据，处理数据，把区域范围内的点位  添加到区域集合列表，并在地图上显示绿色
                for (AreaBean areaBean : areaList) {
                    if (areaBean.isSelect()) {
                        //根据id找到所在区域下的所有数据并添加到区域并集里
                        Level0Item a = levelMap.get(areaBean.getId());
                        if (a != null) {
                            for (Level1Item level1Item : a.getList()) {
                                if (level1Item.getList() != null && level1Item.getList().size() > 0) {
                                    if (!areaCommunityList.contains(level1Item)){
                                        areaCommunityList.add(level1Item);
                                    }
                                }
                                //若只存在区域列表，则直接在地图上显示绿色点位
                                if (locaCommunityList.size()==0){
                                    for (Level2Item level2Item : level1Item.getList()) {
                                        if (level2Item.isValid()) {
                                            level2Item.setSelect(true);
                                            level2Item.setSelectDoorA(true);
                                            level2Item.setSelectDoorB(true);
                                            if (markerMap.get(level2Item) != null) {
                                                markerMap.get(level2Item).setIcon(bitmapGreen);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                twoListInOne();
                //更新左边列表
                showLeftListSelect();
                //计算数量
                computeNumber();
            }

        });
    }

    //两个列表获得并集selectCommunityList
    private void twoListInOne(){
        selectCommunityList.clear();
        if (locaCommunityList.size()==0){
            selectCommunityList.addAll(areaCommunityList);
        }
        if (areaCommunityList.size()==0){
            selectCommunityList.addAll(locaCommunityList);
        }
        //若存在范围列表，则先取两者的集合，再显示绿色区域，没被选中的变成红色
        //先把区域列表和范围列表变成红色，集合再变成绿色
        if (locaCommunityList.size()>0&&areaCommunityList.size()>0){
            for (MultiItemEntity community : locaCommunityList){
                if (community instanceof Level1Item&&areaCommunityList.contains(community)&!selectCommunityList.contains(community)){
                    selectCommunityList.add(community);
                }
            }
            //区域集合变成红色
            listToRed(areaCommunityList);
            //范围集合变成红色
            listToRed(locaCommunityList);
            listToGreen(selectCommunityList);

            Log.e("tag","区域集合数据:"+areaCommunityList.size()+
                    "范围集合数据："+locaCommunityList.size()+
                    "交集集合数据："+selectCommunityList.size());
        }
    }

    //把列表内的数据变成红色
    private void listToRed(List<MultiItemEntity> list){
        for (MultiItemEntity entity : list) {
            if (entity instanceof Level1Item){
                Level1Item community = (Level1Item) entity;
            for (Level2Item level2Item : community.getList()) {
                if (level2Item.isValid()) {
                    level2Item.setSelect(false);
                    level2Item.setSelectDoorA(false);
                    level2Item.setSelectDoorB(false);
                    if (markerMap.get(level2Item) != null) {
                        markerMap.get(level2Item).setIcon(bitmapRed);
                    }
                }
            }
        }}
    }
    private void listToGreen(List<MultiItemEntity> list){
        for (MultiItemEntity entity : list) {
            if (entity instanceof Level1Item){
                Level1Item community = (Level1Item) entity;
            for (Level2Item level2Item : community.getList()) {
                if (level2Item.isValid()) {
                    level2Item.setSelect(true);
                    level2Item.setSelectDoorA(true);
                    level2Item.setSelectDoorB(true);
                    if (markerMap.get(level2Item) != null) {
                        markerMap.get(level2Item).setIcon(bitmapGreen);
                    }
                }
            }}
        }
    }

    //把所有点位设为false
    private void setPointFalse(){
        if (!isFirstChange){
            return;
        }
        for (Level1Item community : communityList){
            community.setSelect(false);
            for (Level2Item door : community.getList()){
                door.setSelect(false);
                door.setSelectDoorA(false);
                door.setSelectDoorB(false);
                if (markerMap.get(door) != null) {
                    markerMap.get(door).setIcon(bitmapRed);
                }
            }
        }
        isFirstChange = false;

    }

    //根据时间，网络获取所有点位
    private void getAllData(long stime, long etime) {
        HttpService.getService().getDoor(stime,etime)
                .enqueue(new Callback<LevelBaseBean>() {
                    @Override
                    public void onResponse(Call<LevelBaseBean> call, Response<LevelBaseBean> response) {
                        if (response.body()!=null&&response.body().getCode()==200){
                            dealData(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<LevelBaseBean> call, Throwable t) {
                        Toast.makeText(SelectPointActivity1.this,"无法获取点位数据，请检查网络数据重新进入此页面",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //根据网络获取的点位处理数据，把所有的点位设为选中状态，地图上为绿色，所有点位添加到全部小区列表communityList
    private void dealData(LevelBaseBean body) {
        levelMap = body.getData();
        //把所有的小区放到同一个列表里面
        communityList = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<Integer,Level0Item> entry : levelMap.entrySet()){
                    Level0Item area = entry.getValue();
                    for (Level1Item community : area.getList()){
                        if (community.getList()==null||community.getList().size()==0){
                            continue;
                        }
                        communityList.add(community);
                        for (Level2Item door : community.getList()){
                            door.setCommunityName(community.getName());
                            //在地图上画出每一个点位
                            drawPointInMap(door);
                            // 点击该小区默认显示定位到该小区的第一个门位置
                            community.setDoor(door);
                        }
                    }
                }

            }
        }).start();
    }

    //在地图上瞄点，并设置每个点位是否为有效点
    private void drawPointInMap(Level2Item door) {
        //判断经纬度是否存在，不存在就return
        if(door.getLocation()==null){
            return;
        }
//        无效点位不在地图上显示
        String[] location = door.getLocation().split(",");
        if (location.length!=2){
            return;
        }
        //判断该点是否有效
        if (stime>door.getAetime()||etime<door.getAstime()){
            door.setaValid(true);
        }

        if (stime>door.getBetime()||etime<door.getBstime()){
            door.setbValid(true);
        }

        //有效就在地图上标点
        if (door.isValid()){
            double lat = Double.valueOf(location[1]);
            double lon = Double.valueOf(location[0]);
            LatLng latLng = new LatLng(lat,lon);
            MarkerOptions markerOptions =new  MarkerOptions();
            markerOptions.position(latLng);
            if (isEdit){
                if (door.getA()==1||door.getB()==1){
                    markerOptions.icon(bitmapGreen);
                }else {
                    markerOptions.icon(bitmapRed);
                }

            }else {
                markerOptions.icon(bitmapGreen);
//                tv.setText(door.getCommunityName()+"\n"+door.getName());
//                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(tv);
//                markerOptions.icon(bitmap);
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("door",door);
            markerOptions.extraInfo(bundle);
            Marker marker = (Marker) baiduMap.addOverlay(markerOptions);
            markerMap.put(door,marker);
        }

    }

    //百度地图关键词搜索器
    private void setSuggestion(){

        sugAdapter.setOnItemClickListen(this);

        suggestionSearch = SuggestionSearch.newInstance();
        //创建sug检索监听器
        listener = new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                listsug.clear();
                listsug.addAll(suggestionResult.getAllSuggestions());
                sugAdapter.notifyDataSetChanged();
            }
        };
        suggestionSearch.setOnGetSuggestionResultListener(listener);
    }
    //范围选点
    public void choosePoint(View v){
        if (isNumberList){
            Toast.makeText(this,"已经选中数量，不可再选区域与定点，若要重选点位需重新进入此页面",Toast.LENGTH_SHORT).show();
            return;
        }
        dialogPoint.show();
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = etSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(info)){
                    //如用户输入非空，发起检索
                    suggestionSearch.requestSuggestion(new SuggestionSearchOption()
                            .city("成都")
                            .keyword(info));
                    rvSearch.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //区域选点,只能添加不能删除
    public void chooseArea(View v){
        if (isNumberList){
            Toast.makeText(this,"已经选中数量，不可再选区域与定点，若要重选点位需重新进入此页面",Toast.LENGTH_SHORT).show();
            return;
        }
        frameLayout.setVisibility(View.VISIBLE);
    }

    public void chooseNumber(View v){
        dialogNumber.show();
        btNumber.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btChooseNumber.setText(etNumber.getText().toString().trim());
                dialogNumber.dismiss();
                //随机选择点位
                judgePointNumber(etNumber.getText().toString().trim());
                //根据数量的结果列表  计算点位数量
                computeNumberToNumber();
                //点击了选择数量，把当前列表改为数量列表
                isNumberList = true;
            }
        });

    }

    //根据数量选中点位放进数组
    private List<MultiItemEntity> numberList = new ArrayList<>();
    int a,b = 0;
    private void judgePointNumber(String number) {
        listToRed(selectCommunityList);
        numberList.clear();
        a= 0;
        b= 0;
        int num = Integer.valueOf(number);



        if (aNum+bNum<num&&aNum+bNum>0){
            Toast.makeText(this,"剩余面数不足，请重新选择", Toast.LENGTH_SHORT).show();
            return;
        }

        if (aNum+bNum==0){
            //也就是说没选择区域和定位
            //需要从全部的小区开始寻找  selectCommunityList.size()==0,需要selectCommunityList 获取全部数据
            setPointFalse();
            //提交数据，处理数据，把区域范围内的点位  添加到区域集合列表，并在地图上显示绿色
            for (AreaBean areaBean : areaList) {
                    //根据id找到所在区域下的所有数据并添加到区域并集里
                    Level0Item a = levelMap.get(areaBean.getId());
                    if (a != null) {
                        for (Level1Item level1Item : a.getList()) {
                            if (level1Item.getList() != null && level1Item.getList().size() > 0) {
                                if (!selectCommunityList.contains(level1Item)){
                                    selectCommunityList.add(level1Item);
                                }
                            }
                        }
                    }
                }

        }
        //加载完毕之后打乱communityList的顺序
        Collections.shuffle(selectCommunityList);

        //一个小区拿一个门，不够的循环补齐
        while (a+b<num){
            for (MultiItemEntity entity : selectCommunityList){
                if (entity instanceof Level1Item){
                    if (a+b==num){ //数据够了之后退出循环
                        break;  //跳出for循环
                    }
                    Level1Item community = (Level1Item) entity;
                    for (Level2Item door :community.getList()){
                        if (a<b) {
                            if (door.isaValid()&&!door.isSelectDoorA()){
                                a++;
                                door.setSelectDoorA(true);
                                if (markerMap.get(door) != null) {
                                    markerMap.get(door).setIcon(bitmapGreen);
                                }
                                judgePointNumberAddList(community);
                                break;
                            }else if (door.isbValid()&&!door.isSelectDoorB()){
                                b++;
                                door.setSelectDoorB(true);
                                if (markerMap.get(door) != null) { markerMap.get(door).setIcon(bitmapGreen); }
                                judgePointNumberAddList(community);
                                break;
                            }
                        }else {
                            if (door.isbValid()&&!door.isSelectDoorB()){
                                b++;
                                door.setSelectDoorB(true);
                                if (markerMap.get(door) != null) {
                                    markerMap.get(door).setIcon(bitmapGreen);
                                }
                                judgePointNumberAddList(community);
                                break;
                            }else if (door.isaValid()&&!door.isSelectDoorA()){
                                a++;
                                door.setSelectDoorA(true);
                                if (markerMap.get(door) != null) {
                                    markerMap.get(door).setIcon(bitmapGreen);
                                }
                                judgePointNumberAddList(community);
                                break;
                            }
                        }
                    }
                }
            }
        }

        //左边栏刷新
        communityAdapter = new CommunityAdapter(numberList);
        rvCommunity.setAdapter(communityAdapter);
        communityAdapter.setCheckInterface(this);
        communityAdapter.setCheckBoxOnClick(this);
    }

    private void judgePointNumberAddList(Level1Item community){
        if (!numberList.contains(community))
            numberList.add(community);
    }

    //点击点位，如天府三街，搜索附近区域，
    @Override
    public void onClick(LatLng latLng) {
        if(latLng==null){
            Toast.makeText(this,"该位置无效，请重新选择",Toast.LENGTH_SHORT).show();
            return;
        }

        //地图上把中心点移到 选中位置
        this.latLng = latLng;
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);

        baiduMap.animateMapStatus(update);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(16));

        MarkerOptions over = new MarkerOptions();
        over.position(latLng);
        over.icon(bitmapFlag);
        baiduMap.addOverlay(over);

        dialogPoint.dismiss();

        dialogDistance.show();

        btDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberStr = etDistance.getText().toString().trim();
                if (!TextUtils.isEmpty(numberStr)){
                    //画半径
                    drawCircle(numberStr);
                    //比较定位是否在范围内
                    compareToCenter(numberStr);
                    //根据compareToCenter已经把在范围内的点位flag变为true,把为true的点位显示在左边栏
                    showLeftListSelect();

                    dialogDistance.dismiss();
                }
            }
        });
    }

    private boolean isFirstChange = true;

    //比较距离，把范围内的小区放到loca列表
    private void compareToCenter(String numberStr) {
        //把所有点位设为false
        setPointFalse();

        for (Level1Item community : communityList){
            for (Level2Item door : community.getList()){
                if (isCirle(numberStr,door.getLocation())){//在范围内,变绿色
                    if (!locaCommunityList.contains(community)){
                        locaCommunityList.add(community);
                    }
                    if (areaCommunityList.size()==0&&markerMap.get(door)!=null){
                        door.setSelect(true);
                        if (door.isaValid()){
                            door.setSelectDoorA(true);
                        }
                        if (door.isbValid()){
                            door.setSelectDoorB(true);
                        }
                        markerMap.get(door).setIcon(bitmapGreen);
                    }
                }
            }
        }

        twoListInOne();
        //更新左边列表
        showLeftListSelect();
        //计算数量
        computeNumber();
    }

    private void computeNumber() {

        aNum = 0;
        bNum = 0;
        doorNum = 0;
        communityNum = 0;
        for (MultiItemEntity entity : selectCommunityList){
            if (entity instanceof Level1Item){
                communityNum++;
                Level1Item community = (Level1Item) entity;
                for (Level2Item door : community.getList()){
                    doorNum++;
                    if (door.isSelectDoorA()&&door.isaValid()){
                        aNum++;
                    }

                    if (door.isSelectDoorB()&&door.isbValid()){
                        bNum++;
                    }


                }
            }

        }

        tvDoorInfo.setText(communityNum+"个小区,总共"+doorNum+"道门\n选中" +
                aNum+"个A面,"+
                bNum+"个B面");
    }

    private void computeNumberToNumber() {

        aNum = 0;
        bNum = 0;
        doorNum = 0;
        communityNum = 0;
        for (MultiItemEntity entity : numberList){
            if (entity instanceof Level1Item){
                Level1Item community = (Level1Item) entity;
                communityNum ++ ;
                for (Level2Item door : community.getList()){
                    doorNum++;
                    if (door.isSelectDoorA()&&door.isaValid()){
                        aNum++;
                    }

                    if (door.isSelectDoorB()&&door.isbValid()){
                        bNum++;
                    }
                }
            }

        }

        tvDoorInfo.setText(communityNum+"个小区,,总共"+doorNum+"道门\n选中" +
                aNum+"个A面,"+
                bNum+"个B面");
    }

    private void drawCircle(String numberStr) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng)
                .fillColor(0x220000FF)
                .radius(Integer.valueOf(numberStr)*1000);
        baiduMap.addOverlay(circleOptions);
    }

    private CommunityAdapter communityAdapter;
    private void showLeftListSelect() {
        communityAdapter = new CommunityAdapter(selectCommunityList);
        rvCommunity.setAdapter(communityAdapter);
        communityAdapter.setCheckInterface(this);
        communityAdapter.setCheckBoxOnClick(this);
    }

    private boolean isCirle(String radio,String location){
        //半径 ：公里
        double length = Double.valueOf(radio);
        String[] strings = location.split(",");
        if(strings.length == 2){
            double longitude = Double.valueOf(strings[0]);
            double latitude = Double.valueOf(strings[1]);
            double distance = getDistance(latLng.longitude,latLng.latitude,longitude,latitude);
            if (distance<length*1000){
                return true;
            }
        }
        return false;
    }


    private double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        //纬度
        double lat1 = (Math.PI / 180) * latitude1;
        double lat2 = (Math.PI / 180) * latitude2;

        //经度
        double long1 = (Math.PI / 180) * longitude1;
        double long2 = (Math.PI / 180) * longitude2;

        //地球半径
        double R = 6371;

        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(long2 - long1)) * R;

        return d*1000;
    }

    @Override
    public void checkBoxChangeCommunity(Level2Item lv2) {
        mapShowDoor(lv2);
    }

    @Override
    public void checkBoxChangeCommunity(Level1Item lv1) {
        mapShowDoor(lv1.getDoor());
    }

    private void mapShowDoor(Level2Item lv2){
        String[] strings = lv2.getLocation().split(",");
        if (strings.length==2){
            LatLng la = new LatLng(Double.valueOf(strings[1]),Double.valueOf(strings[0]));
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(la);
            baiduMap.animateMapStatus(update);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(19));
        }

    }

    @Override
    public void onCheckA(boolean isCheck, Level2Item door) {
        Log.e("tag","ischeck:"+isCheck+" door is ab:"+door.isSelect()+door.isSelectDoorA()+door.isSelectDoorB());
        door.setSelectDoorA(isCheck);
        changeABStatus(door);
    }

    @Override
    public void onCheckB(boolean isCheck, Level2Item door) {
        Log.e("tag","ischeck:"+isCheck+" door is ab:"+door.isSelect()+door.isSelectDoorA()+door.isSelectDoorB());
        door.setSelectDoorB(isCheck);
        changeABStatus(door);
    }

    @Override
    public void onCheckBox(boolean isCheck, Level2Item door) {
        door.setSelect(isCheck);
        door.setAllab(isCheck);
        changeABStatus(door);
    }



    private void changeABStatus(Level2Item door){
        //改变列表
        communityAdapter.notifyDataSetChanged();
        //改变地图点位
        if (markerMap.get(door)!=null){
            markerMap.get(door).setIcon(door.isSelectDoorA()||door.isSelectDoorB()?bitmapGreen:bitmapRed);
        }
        //计算点位数量
        computeNumber();
    }

    //点击提交按钮，弹框，保存还是确定下单
    AlertDialog alertDialog;
    public void onSubmit(View view){
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择提交方式")
                .setMessage("选中"+communityNum+"个小区,"+doorNum+"道门\n" +
                        aNum+"个A面,"+
                        bNum+"个B面")
                .setNegativeButton("保存订单", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onSubmitData(0);
                    }
                })
                .setPositiveButton("确定下单", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onSubmitData(1);
                    }
                }).create();

        alertDialog.show();
    }



    private List<Level2Item> doorSubmitList = new ArrayList<>();
    //状态0保存1确认  status 是否是保存
    private void onSubmitData(int status){
        doorSubmitList.clear();
        if (selectCommunityList.size()==0){
            Toast.makeText(this,"没有数据，请选择点位后再提交",Toast.LENGTH_SHORT).show();
            return;
        }
        for (MultiItemEntity entity : isNumberList?numberList:selectCommunityList){
            if (entity instanceof Level1Item){
                Level1Item community = (Level1Item) entity;
                for (Level2Item door : community.getList()){
                    if (door.isSelect()){
                        doorSubmitList.add(door);
                    }
                }
            }
        }
        Map<String,String> map = new HashMap<>();
        map.put("com_id", String.valueOf(companyId));
        map.put("uid", String.valueOf(SharedPreferencesUtil.queryUserID(this)));
        map.put("stime", String.valueOf(stime));
        map.put("etime", String.valueOf(etime));
        map.put("datas",doorSubmitList.toString());
        map.put("status", String.valueOf(status));
        HttpService.getService().setAreamap(map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //上传成功，返回数据为
                try {
                    String msg = response.body().string();
                    JSONObject jsonObject = new JSONObject(msg);
                    String url = jsonObject.getString("url");

                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Label", url);
                    // 将ClipData内容放到系统剪贴板里。
                    clipboardManager.setPrimaryClip(mClipData);
                    Toast.makeText(SelectPointActivity1.this,"上传成功，上次结果已经复制到剪贴板，请打开浏览器粘贴",Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    Toast.makeText(SelectPointActivity1.this,"上传失败，服务器返回"+e.toString(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SelectPointActivity1.this,"上传失败，服务器返回"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SelectPointActivity1.this,"上传失败，服务器返回"+t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    BDLocation bdLocation;
    /**
     * 获取当前定位
     * @param bd
     */
    @Override
    public void onLocation(@NotNull BDLocation bd) {

        bdLocation = bd;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
