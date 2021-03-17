package com.example.myapplication.weight.taobao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapplication.R;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class TaoBaoActivity extends AppCompatActivity {

    //不同item必须不同的viewtype
    int BANNER_VIEW_TYPE = 1;
    int MENU_VIEW_TYPE = 2;
    int NEWS_VIEW_TYPE = 3;
    int TITLE_VIEW_TYPE = 4;
    int GRID_VIEW_TYPE = 5;
    //广告位
    int[] ITEM_URL = {R.mipmap.item1, R.mipmap.item2, R.mipmap.item3, R.mipmap.item4, R.mipmap.item5};
    //    应用位
    String[] ITEM_NAMES = {"天猫", "聚划算", "天猫国际", "外卖", "天猫超市", "充值中心", "飞猪旅行", "领金币", "拍卖", "分类"};
    int[] IMG_URLS = {R.mipmap.ic_tian_mao, R.mipmap.ic_ju_hua_suan, R.mipmap.ic_tian_mao_guoji, R.mipmap.ic_waimai, R.mipmap.ic_chaoshi, R.mipmap.ic_voucher_center, R.mipmap.ic_travel, R.mipmap.ic_tao_gold, R.mipmap.ic_auction, R.mipmap.ic_classify};
    //    高颜值商品位
    int[] GRID_URL = {R.mipmap.flashsale1, R.mipmap.flashsale2, R.mipmap.flashsale3, R.mipmap.flashsale4};
    private RecyclerView mRecyclerView;

    RecyclerView rv;
    List<DelegateAdapter.Adapter> adapterList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_bao);

        adapterList = new ArrayList<>();

        rv = findViewById(R.id.rv);

        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
        rv.setLayoutManager(virtualLayoutManager);
        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）：
        //TODO 缓存由viewType决定 一个ViewType表示一种样式的View 会存储到同一个缓存列表中去
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(0, 10);
        rv.setRecycledViewPool(viewPool);

        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);

        LinearLayoutHelper bannerHelper = new LinearLayoutHelper(0,1);
        BaseAdapter bannerAdapter = new BaseAdapter(this,R.layout.vlayout_banner,1,bannerHelper,BANNER_VIEW_TYPE){
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add("http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=%E5%A3%81%E7%BA%B8&hs=0&pn=0&spn=0&di=121220&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cl=2&lm=-1&cs=3702648800%2C3967102116&os=3758495491%2C3098688923&simid=4294009294%2C627451469&adpicid=0&lpn=0&ln=30&fr=ala&fm=&sme=&cg=wallpaper&bdtype=0&oriquery=%E5%A3%81%E7%BA%B8&objurl=http%3A%2F%2Fd.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F6a63f6246b600c334c3e91cb1e4c510fd9f9a16a.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fr5oj6_z%26e3Bkwt17_z%26e3Bv54AzdH3Fq7jfpt5gAzdH3Fmdc8cncnca9ncdbnd9_z%26e3Bip4s%3Fqks%3D6jswpj_q7jfpt5g_a&gsm=1&islist=&querylist=");
                arrayList.add("http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=%E5%A3%81%E7%BA%B8&hs=0&pn=1&spn=0&di=207460&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cl=2&lm=-1&cs=3468842248%2C1928586949&os=2341603474%2C2591036361&simid=3405762237%2C344604069&adpicid=0&lpn=0&ln=30&fr=ala&fm=&sme=&cg=wallpaper&bdtype=0&oriquery=%E5%A3%81%E7%BA%B8&objurl=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201111%2F21%2F205700txzuacubbcy91u99.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B4t7t_z%26e3Bv54AzdH3Fu5674_z%26e3Brir%3F451%3Detjopi6jw1%26pt1%3Dnndbbl%26jxp6w%3Drw2j%25nD9%25dm561j6ky%25nD1wpjstgj%26rw2j%3D8&gsm=1&islist=&querylist=");
                // 绑定数据
                Banner mBanner = holder.getView(R.id.banner);
                //设置banner样式
                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                //设置图片加载器
                mBanner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                mBanner.setImages(arrayList);
                //设置banner动画效果
                mBanner.setBannerAnimation(Transformer.DepthPage);
                //设置标题集合（当banner样式有显示title时）
                //        mBanner.setBannerTitles(titles);
                //设置自动轮播，默认为true
                mBanner.isAutoPlay(true);
                //设置轮播时间
                mBanner.setDelayTime(5000);
                //设置指示器位置（当banner模式中有指示器时）
                mBanner.setIndicatorGravity(BannerConfig.CENTER);
                //banner设置方法全部调用完毕时最后调用
                mBanner.start();

                mBanner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Toast.makeText(getApplicationContext(), "banner点击了" + position, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
        adapterList.add(bannerAdapter);
        //menu
        // 在构造函数设置每行的网格个数
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(5);
        gridLayoutHelper.setPadding(0, 16, 0, 16);
        gridLayoutHelper.setVGap(16);// 控制子元素之间的垂直间距
        gridLayoutHelper.setHGap(0);// 控制子元素之间的水平间距
        gridLayoutHelper.setBgColor(Color.WHITE);

        BaseAdapter menuAdapter = new BaseAdapter(this,  R.layout.vlayout_menu
                , 10,gridLayoutHelper, MENU_VIEW_TYPE) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                holder.setText(R.id.tv_menu_title_home, ITEM_NAMES[position] + "");
                holder.setImageResource(R.id.iv_menu_home, IMG_URLS[position]);
                holder.getView(R.id.ll_menu_home).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), ITEM_NAMES[position], Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        adapterList.add(menuAdapter);
        //news
        BaseAdapter newsAdapter = new BaseAdapter(this
                , R.layout.vlayout_news, 1, new LinearLayoutHelper(), NEWS_VIEW_TYPE) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                MarqueeView marqueeView1 = holder.getView(R.id.marqueeView1);
                MarqueeView marqueeView2 = holder.getView(R.id.marqueeView2);

                List<String> info1 = new ArrayList<>();
                info1.add("天猫超市最近发大活动啦，快来抢");
                info1.add("没有最便宜，只有更便宜！");

                List<String> info2 = new ArrayList<>();
                info2.add("这个是用来搞笑的，不要在意这写小细节！");
                info2.add("啦啦啦啦，我就是来搞笑的！");

                marqueeView1.startWithList(info1);
                marqueeView2.startWithList(info2);
                // 在代码里设置自己的动画
                marqueeView1.startWithList(info1, R.anim.anim_bottom_in, R.anim.anim_top_out);
                marqueeView2.startWithList(info2, R.anim.anim_bottom_in, R.anim.anim_top_out);

                marqueeView1.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, TextView textView) {
                        Toast.makeText(getApplicationContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                marqueeView2.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, TextView textView) {
                        Toast.makeText(getApplicationContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        adapterList.add(newsAdapter);

        FloatLayoutHelper floatLayoutHelper = new FloatLayoutHelper();
        floatLayoutHelper.setAlignType(FixLayoutHelper.BOTTOM_LEFT);
        floatLayoutHelper.setX(200);
        floatLayoutHelper.setY(200);
        BaseAdapter floatAdapter = new BaseAdapter(this,R.layout.item,1,floatLayoutHelper,0);
        adapterList.add(floatAdapter);

        //这里我就循环item 实际项目中不同的ITEM 继续往下走就行
        for (int i = 0; i < ITEM_URL.length; i++) {

            //item1 title
            final int finalI = i;
            BaseAdapter titleAdapter = new BaseAdapter(this, R.layout.vlayout_title, 1, new LinearLayoutHelper(), TITLE_VIEW_TYPE) {
                @Override
                public void onBindViewHolder(BaseViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                    holder.setImageResource(R.id.iv, ITEM_URL[finalI]);
                }
            };
            adapterList.add(titleAdapter);
            //item1 gird
            GridLayoutHelper gridLayoutHelper1 = new GridLayoutHelper(2);
            gridLayoutHelper.setMargin(0, 0, 0, 0);
            gridLayoutHelper.setPadding(0, 0, 0, 0);
            gridLayoutHelper.setVGap(0);// 控制子元素之间的垂直间距
            gridLayoutHelper.setHGap(0);// 控制子元素之间的水平间距
            gridLayoutHelper.setBgColor(Color.WHITE);
            gridLayoutHelper.setAutoExpand(true);//是否自动填充空白区域
            BaseAdapter girdAdapter = new BaseAdapter(this, R.layout.vlayout_grid
                    , 4, gridLayoutHelper1, GRID_VIEW_TYPE) {
                @Override
                public void onBindViewHolder(BaseViewHolder holder, final int position) {
                    super.onBindViewHolder(holder, position);
                    int item = GRID_URL[position];
                    ImageView iv = holder.getView(R.id.iv);
                    Glide.with(getApplicationContext()).load(item).into(iv);

                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "item" + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            };
            adapterList.add(girdAdapter);
        }
        delegateAdapter.addAdapters(adapterList);

    }
}
