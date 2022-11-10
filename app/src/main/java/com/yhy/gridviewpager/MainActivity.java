package com.yhy.gridviewpager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yhy.gvp.adapter.GVPAdapter;
import com.yhy.gvp.listener.OnItemClickListener;
import com.yhy.gvp.listener.OnItemLongClickListener;
import com.yhy.gvp.widget.GridViewPager;
import com.yhy.widget.core.img.round.CircleImageView;
import com.yhy.widget.core.recycler.div.RvDivider;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final int[] IMG_ARR = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
            R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j,
            R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p,
            R.drawable.q, R.drawable.r, R.drawable.s, R.drawable.t, R.drawable.u, R.drawable.v,
            R.drawable.w, R.drawable.x, R.drawable.y, R.drawable.z, R.drawable.u, R.drawable.v,
            R.drawable.aa, R.drawable.bb, R.drawable.cc, R.drawable.dd, R.drawable.ee, R.drawable.ff};

    private final List<ItemData> mDataList = new ArrayList<>();

    private GridViewPager gridViewpager;
    MagicIndicator indicatorContainer;
    IndexTypeAdapter indexTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        gridViewpager = (GridViewPager) findViewById(R.id.gvp_content_a);
        indicatorContainer=findViewById(R.id.indicator_container);
        for (int i = 0; i < IMG_ARR.length; i++) {
            mDataList.add(new ItemData(IMG_ARR[i], "Item " + i));
        }


//        // 创建适配器
//        final MyAdapter adapter = new MyAdapter(R.layout.layout_item, mDataList);
//
//        // 设置相关事件
//        adapter.setOnItemClickListener(new OnItemClickListener<ItemData>() {
//
//            @Override
//            public void onItemClick(View view, int position, ItemData data) {
//                toast("点击了 " + data.name);
//            }
//        });
//        adapter.setOnItemLongClickListener(new OnItemLongClickListener<ItemData>() {
//
//            @Override
//            public boolean onItemLongClick(View view, int position, ItemData data) {
//                toast("长按了 " + data.name + ", 每页增加一条数据");
//                gvpContentA.setPageSize(gvpContentA.getPageSize() + 1);
//                gvpContentA.notifyDataSetChanged();
//                return false;
//            }
//        });
//
//        // 设置分割线
//        adapter.setItemDecoration(new RvDivider.Builder(this).color(Color.TRANSPARENT).widthDp(10).type(RvDivider.DividerType.TYPE_WITH_START_END).build());
//        // 设置适配器
//        gvpContentA.setGVPAdapter(adapter);



        indexTypeAdapter=new IndexTypeAdapter(this,R.layout.item_index_type,mDataList);//页面内容适配器
        gridViewpager.setGVPAdapter(indexTypeAdapter);

        Log.i("datas",(int) Math.ceil(mDataList.size() / 10)+"");
        CommonNavigator commonNavigator = new CommonNavigator(this);//指示器
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                int num=mDataList.size()/10;
                if(mDataList.size() % 10 != 0){
                    num++;
                }
                return mDataList==null?0:num;
            }

            @Override
            public IPagerTitleView getTitleView(Context mContext, final int i) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(MainActivity.this);
                View view=View.inflate(MainActivity.this,R.layout.single_image_layout,null);
                final ImageView iv_image=view.findViewById(R.id.iv_image);
                iv_image.setImageResource(R.drawable.point_unfocused);

                commonPagerTitleView.setContentView(view);//指示器引入外部布局，可知指示器内容可根据需求设置，多样化
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int i, int i1) {
                        iv_image.setImageResource(R.drawable.point_focused);
                    }

                    @Override
                    public void onDeselected(int i, int i1) {
                        iv_image.setImageResource(R.drawable.point_unfocused);
                    }

                    @Override
                    public void onLeave(int i, int i1, float v, boolean b) {

                    }

                    @Override
                    public void onEnter(int i, int i1, float v, boolean b) {

                    }
                });
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        indicatorContainer.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicatorContainer, gridViewpager);//页面内容与指示器关联


    }








    public class IndexTypeAdapter extends GVPAdapter<ItemData> {
        private Context context;
        public IndexTypeAdapter(Context context,int layoutResId, @Nullable List<ItemData>  data) {
            super(layoutResId, data);
            this.context=context;
        }


        @Override
        public void bind(View item, int position, ItemData data) {
            CircleImageView iv_image=item.findViewById(R.id.iv_image);
            TextView tv_type_name=item.findViewById(R.id.tv_type_name);
            Picasso.get().load(data.imgId).into(iv_image);

            tv_type_name.setText(data.name);
        }
    }
//IndexAllTypeBean.TypeListBean 为数据内容实体类，不做介绍



    /**
     * 自定义适配器
     */
//    private class MyAdapter extends GVPAdapter<ItemData> {
//
//        public MyAdapter(int layoutId, List<ItemData> dataList) {
//            super(layoutId, dataList);
//        }
//
//        @Override
//        public void bind(View item, int position, ItemData data) {
//            // 在这里绑定每个itemView的相关数据
//            ImageView ivImg = (ImageView) item.findViewById(R.id.iv_img);
//            TextView tvName = (TextView) item.findViewById(R.id.tv_name);
//
//            ivImg.setImageResource(data.imgId);
//            tvName.setText(data.name);
//        }
//    }

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
