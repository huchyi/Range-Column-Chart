package com.example.huangchuanyi.agraphicalviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hcy 2016-04-28
 *         <p/>
 *         绘制自定义range柱状图
 */
public class ColumnChartView extends View {

    private Context mContent;

    private Paint mGrayLinePaint;
    private Paint mBlueLinePaint;
    private Paint mRectPaint;
    private Paint mCirclePaint;


    private int width = 600;
    private int height = 450;

    private int maxpef = 0;
    private int minpef = 0;
    private int monthNum = 31;

    private int touchX = 0;
    private int mPosition = 0;

    private int pefTimes = 1; //当前控件和高度和pef最大值得比值,确保圆柱不会太小或者太大.
    private int offSet = 15; //圆柱的间隔
    private int space = 6; //圆柱的宽度
    private int marginLeft = 0; //圆柱的左间距
    private int marginRight = 0; //圆柱的右间距
    private int marginBottom = 10;//设置底部的距离
    private int marginTop = 4;


    private List<PefListData> mList = new ArrayList<>();
    private List<PefListData> mList2 = new ArrayList<>();


    public ColumnChartView(Context context) {
        super(context);
        init(context);
    }

    public ColumnChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContent = context;
        mGrayLinePaint = new Paint();
        mGrayLinePaint.setColor(Color.parseColor("#ededed"));
        mGrayLinePaint.setAntiAlias(true);
        mGrayLinePaint.setStrokeWidth(dip2px(context, 0.6f));
        mGrayLinePaint.setStyle(Paint.Style.FILL);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.parseColor("#d8d8d8"));
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStrokeWidth(dip2px(context, 0.6f));
        mCirclePaint.setStyle(Paint.Style.FILL);


        mBlueLinePaint = new Paint();
        mBlueLinePaint.setColor(Color.parseColor("#6aa8f0"));
        mBlueLinePaint.setAntiAlias(true);
        mBlueLinePaint.setStrokeWidth(dip2px(context, 0.8f));
        mBlueLinePaint.setStyle(Paint.Style.FILL);


        mRectPaint = new Paint();
        mRectPaint.setColor(Color.parseColor("#a7aaa9"));
        mRectPaint.setAntiAlias(true);
        mRectPaint.setStyle(Paint.Style.FILL);


        offSet = dip2px(context, 7f);
        space = dip2px(context, 2.5f);
        marginLeft = dip2px(context, 0);
        marginRight = dip2px(context, 0);
        marginBottom = dip2px(context, 4f);
        marginTop = dip2px(context, 1.3f);
    }


    private Line2ViewListener mLine2ViewListener;

    public interface Line2ViewListener {

        void onChangeItem(int positon, List<PefListData> pefListDatas);

        void onFinishItem(int positon, List<PefListData> pefListDatas);
    }

    public void setOnItemListener(Line2ViewListener mLine2ViewListener) {
        this.mLine2ViewListener = mLine2ViewListener;
    }


    public void setData(int year, int month) {

        monthNum = isBigOrLittleMonth(year, month);
        Log.i("hcy", "monthNum:" + monthNum);

        for (int i = 1; i < monthNum + 1; i++) {
            PefListData pefListData = new PefListData();
            pefListData.date = "2016-04-" + (i < 10 ? "0" + i : i);
            pefListData.dayIndex = i + "";
            int pef = (int) (100 * Math.random());

            if (pef > maxpef) {
                maxpef = pef;
            }
            if (pef < minpef) {
                minpef = pef;
            }

            pefListData.pef = pef + "";
            pefListData.timeType = "1";
            mList.add(pefListData);
        }


        for (int i = 1; i < monthNum + monthNum; i++) {
            PefListData pefListData = new PefListData();
            pefListData.date = "2016-04-" + (i < 10 ? "0" + i : i);
            pefListData.dayIndex = i + "";
            int pef = (int) (100 * Math.random());

            if (pef > maxpef) {
                maxpef = pef;
            }
            if (pef < minpef) {
                minpef = pef;
            }

            pefListData.pef = pef + "";
            pefListData.timeType = "2";
            mList2.add(pefListData);
        }
        Log.i("hcy", "maxpef:" + maxpef + ",minpef:" + minpef);


        touchX = offSet + marginLeft + space / 2;
        mPosition = 0;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLineGray(canvas);
        drawLineBlue(canvas);
        drawRectView(canvas);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("hcy", "w:" + w);
        width = w;
        height = h;
        change();
    }

    private void change() {

        //重新计算每个pef的高度
        maxpef = maxpef <= 0 ? 1 : maxpef;
        pefTimes = (height - marginBottom) / maxpef;
        if (mList != null && mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                PefListData pefListData = mList.get(i);
                int pef = Integer.valueOf(pefListData.pef);
                mList.get(i).setPef(pef * pefTimes + "");
            }
        }

        if (mList2 != null && mList2.size() > 0) {
            for (int i = 0; i < mList2.size(); i++) {
                PefListData pefListData = mList2.get(i);
                int pef = Integer.valueOf(pefListData.pef);
                mList2.get(i).setPef(pef * pefTimes + "");
            }
        }

        //重新计算pef圆柱的间隔
        offSet = (width - marginLeft - marginRight) / (monthNum + 1);

        //蓝线的初始位置
        touchX = offSet + marginLeft + space / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                changeBlueLine(x);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLine2ViewListener.onFinishItem(mPosition, mList);
                break;
            default:
                break;
        }
        return true;
    }

    private void changeBlueLine(int x) {
        for (int i = 0; i < monthNum; i++) {
            int maxX = offSet * (i + 1) + marginLeft + space + offSet / 2;
            int minX = offSet * (i + 1) + marginLeft - offSet / 2;
            if (minX < x && maxX > x) {
                if (mPosition != i) {
                    touchX = offSet * (i + 1) + marginLeft + space / 2;
                    mPosition = i;
                    mLine2ViewListener.onChangeItem(mPosition, mList);
                    invalidate();
                }
                break;
            }
        }
    }


    //画矩形圆柱
    private void drawRectView(Canvas canvas) {
        for (int i = 0; i < monthNum; i++) {
            int y1 = 0;
            int y2 = 0;
            try {
                y1 = height - marginBottom - Integer.valueOf(mList.get(i).pef);
                y2 = height - marginBottom - Integer.valueOf(mList2.get(i).pef);
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }catch (NullPointerException e){
                e.printStackTrace();
            }catch (ClassCastException e){
                e.printStackTrace();
            }

            int sy = (y1 < y2) ? y1 : y2;
            float difY = (float) Math.abs(y1 - y2) / (sy <= 0 ? 1 : sy);
//            Log.i("hcy","-------------------------------------");
//            Log.i("hcy","date:" + mList.get(i).date + ",difY:" + difY);
//            Log.i("hcy","y1:" + y1);
//            Log.i("hcy","y2:" + y2);
//            Log.i("hcy","sy:" + sy);

            if (difY > 1) {
                mRectPaint.setColor(Color.parseColor("#f1ab5e"));
            } else if (mPosition == i) {
                mRectPaint.setColor(Color.parseColor("#6aa8f0"));
            } else {
                mRectPaint.setColor(Color.parseColor("#a7aaa9"));
            }

            if (y1 > 0 || y2 > 0) {
                if (Math.abs(y2 - y1) > 6) {
                    RectF rectF;
                    if (y2 < y1) {
                        rectF = new RectF(offSet * (i + 1) + marginLeft,
                                y2,
                                offSet * (i + 1) + marginLeft + space,
                                y1);// 设置长方形
                    } else {
                        rectF = new RectF(offSet * (i + 1) + marginLeft,
                                y1,
                                offSet * (i + 1) + marginLeft + space,
                                y2);// 设置长方形
                    }
                    canvas.drawRoundRect(rectF, space / 2, space / 2, mRectPaint);//圆角矩形.第二个参数是x半径，第三个参数是y半径
                } else {
                    canvas.drawCircle(offSet * (i + 1) + marginLeft + space / 2, y1 + Math.abs(y1 - y2) / 2, space / 2, mRectPaint);
                }
            }

        }
    }


    private void drawLineGray(Canvas canvas) {
        //画背景的灰线
        canvas.drawLine(offSet + marginLeft + space / 2, height - marginBottom, offSet * 31 + marginLeft + space / 2, height - marginBottom, mGrayLinePaint);// 画线

        canvas.drawLine(offSet + marginLeft + space / 2, height / 2, offSet * 31 + marginLeft + space / 2, height / 2, mGrayLinePaint);// 画线

        canvas.drawLine(offSet + marginLeft + space / 2, 2, offSet * 31 + marginLeft + space / 2, 2, mGrayLinePaint);// 画线

        //画底部的灰线的圆点
        for (int i = 0; i < 31; i++) {
            if (i == 0 || i == 5
                    || i == 10
                    || i == 15
                    || i == 20
                    || i == 25
                    || i == 30) {
                canvas.drawCircle(offSet * (i + 1) + marginLeft + space / 2, height - marginBottom, 3, mCirclePaint);
            }
        }
    }


    //画选中的蓝线
    private void drawLineBlue(Canvas canvas) {
        //蓝色选中的线
        canvas.drawLine(touchX, marginTop, touchX, height - marginBottom, mBlueLinePaint);// 画线

        //蓝色选中的圆
        canvas.drawCircle(touchX, height - marginBottom, 5, mBlueLinePaint);
    }


    /**
     * 判断大小月
     *
     * @param year  年
     * @param month 月
     * @return
     */
    private int isBigOrLittleMonth(int year, int month) {
        String[] monthsBig = {"1", "3", "5", "7", "8", "10", "12"};
        String[] monthsLittle = {"4", "6", "9", "11"};
        int days = 0;
        final List<String> listBig = Arrays.asList(monthsBig);
        final List<String> listLittle = Arrays.asList(monthsLittle);
        if (listBig.contains(String.valueOf(month))) {//大月
            days = 31;

        } else if (listLittle.contains(String.valueOf(month))) {//小月
            days = 30;
        } else {//2月
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                days = 29;
            } else {
                days = 28;
            }

        }
        return days;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
