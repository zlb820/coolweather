package com.zlb.bingo.coolweather.activity.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zlb.bingo.coolweather.activity.model.City;
import com.zlb.bingo.coolweather.activity.model.County;
import com.zlb.bingo.coolweather.activity.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bingo on 2016/01/30.
 */
public class CoolWeatherDB {
  /*
  * 创建数据库函数的第二个参数将接受一个数据库名的参数，这里定义我们自己的数据库名*/
    public  static  final String DB_NAME="cool_weather";
    /*
    * 数据库的版本
    * */
    public  static  final int VERSION= 1;

    private  static CoolWeatherDB coolWeatherDB;

    private SQLiteDatabase db;

    /*将构造方法私有化，这样在全局只会有一个coolWeahterDB的实例*/
    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper=new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
        db=dbHelper.getWritableDatabase();
    }
    /*提供获取coolWeatherDB实例的接口
    * */
    public synchronized static CoolWeatherDB getInstance(Context context){
        if (coolWeatherDB==null){
            coolWeatherDB=new CoolWeatherDB(context);
        }
        return  coolWeatherDB;
    }

    /*将province实例存储到数据库
    * */
    public void saveProvince(Province province){
        if (province !=null){
            ContentValues values=new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }
    /*从数据库读取全国所有的省份信息*/
    public List<Province> loadProvinces(){
        List<Province> list=new ArrayList<Province>();
        Cursor cursor=db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                //new Province的对象
                Province province=new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
            }while (cursor.moveToNext());


        }
        return list;
    }

    /*将City 实例存储到数据库*/
    public  void saveCity(City city){
        if (city !=null){
            ContentValues values=new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
        }
    }
    /*从数据库读取某省下所有的城市信息*/
    public List<City> loadCities(int provinceId){
        List<City> list=new ArrayList<City>();

        Cursor cursor=db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()){
            do {
                City city=new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);

            }while (cursor.moveToNext());
        }

        return list;
    }
    /*将county实例存储到数据库*/
    public void saveCounty(County county){
        if (county!=null){
         ContentValues values =new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            db.insert("County",null,values);
        }

    }
    /*从数据库读取某城市下所有的县信息*/
    public List<County> loadCounties(int cityId){
        List<County> list=new ArrayList<County>();
        Cursor cursor=db.query("County", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()){
            do {
              County county=new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);

            }while(cursor.moveToNext());

            }



        return list;
    }
}









