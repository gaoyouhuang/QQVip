package com.example.myapplication;//package com.example.myapplication;
//import android.app.Dialog;
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.google.android.material.bottomsheet.BottomSheetBehavior;
//import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Author: david.lvfujiang
// * @Date: 2019/11/14
// * @Describe:
// */
//public class BaseFullBottomSheetFragment extends BottomSheetDialogFragment {
//
//    private List<ShareItem> mShareList = new ArrayList<>();
//    private int[] imgArry= {R.mipmap.five,R.mipmap.four,R.mipmap.one,R.mipmap.three};
//    private Context mContext;
//    private View view;
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        Log.e("TAG", "onCreateDialog: ");
//        //返回BottomSheetDialog的实例
//        return new BottomSheetDialog(this.getContext());
//    }
//
//
//    @Override
//    public void onStart() {
//        Log.e("TAG", "onStart: ");
//        super.onStart();
//        //获取dialog对象
//        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
//        //把windowsd的默认背景颜色去掉，不然圆角显示不见
//        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        //获取diglog的根部局
//        FrameLayout bottomSheet = dialog.getDelegate().findViewById(R.id.design_bottom_sheet);
//        if (bottomSheet != null) {
//            //获取根部局的LayoutParams对象
//            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
//            layoutParams.height = getPeekHeight();
//            //修改弹窗的最大高度，不允许上滑（默认可以上滑）
//            bottomSheet.setLayoutParams(layoutParams);
//
//            final BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
//            //peekHeight即弹窗的最大高度
//            behavior.setPeekHeight(getPeekHeight());
//            // 初始为展开状态
//            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//            ImageView mReBack = view.findViewById(R.id.re_back_img);
//            //设置监听
//            mReBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //关闭弹窗
//                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//                }
//            });
//        }
//
//    }
//
//    /**
//     * 弹窗高度，默认为屏幕高度的四分之三
//     * 子类可重写该方法返回peekHeight
//     *
//     * @return height
//     */
//    protected int getPeekHeight() {
//        int peekHeight = getResources().getDisplayMetrics().heightPixels;
//        //设置弹窗高度为屏幕高度的3/4
//        return peekHeight - peekHeight / 3;
//    }
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mContext = getContext();
//        Log.e("TAG", "onCreateView: ");
//        view = inflater.inflate(R.layout.layoyt_bottomsheet, container, false);
//        initData();
//        initViews(view);
//        return view;
//    }
//
//    private void initViews(View view) {
//        RecyclerView recyclerView = view.findViewById(R.id.fragment_share_recyclerView);
//        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
//        RecyclerCommonAdapter adapter = new RecyclerCommonAdapter(R.layout.recyclear_item, mShareList);
//        recyclerView.setAdapter(adapter);
//    }
//
//    private void initData() {
//
//        for (int i = 0; i < 30; i++) {
//            ShareItem item = new ShareItem();
//            item.setIcon(imgArry[i%4]);
//            mShareList.add(item);
//        }
//
//    }
//
//}