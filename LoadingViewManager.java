import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

/**
 * @author BarefootBKK
 * @date 2019/03/17
 */
public class LoadingViewManager {
    public final static int MATCH_PARENT = RelativeLayout.LayoutParams.MATCH_PARENT;
    public final static int WRAP_CONTENT = RelativeLayout.LayoutParams.WRAP_CONTENT;

    private static LoadingViewContainer loadingViewContainer;

    public static LoadingViewContainer with(Activity activity) {
        loadingViewContainer = new LoadingViewContainer(activity);
        return loadingViewContainer;
    }

    public LoadingViewManager(Activity activity) {
        loadingViewContainer = new LoadingViewContainer(activity);
    }

    public LoadingViewContainer getLoadingViewContainer() {
        return loadingViewContainer;
    }

    public static void dismiss() {
        if (loadingViewContainer != null) {
            loadingViewContainer.dismiss(false);
            loadingViewContainer = null;
        }
    }

    public static void dismiss(boolean isForcedDismiss) {
        if (loadingViewContainer != null) {
            loadingViewContainer.dismiss(isForcedDismiss);
            loadingViewContainer = null;
        }
    }

    public static void updateHintText(String hintText) {
        if (loadingViewContainer != null) {
            loadingViewContainer.setHintText(hintText);
        }
    }

    public static boolean isShowing() {
        if (loadingViewContainer == null) {
            return false;
        } else {
            return loadingViewContainer.isShowing();
        }
    }

    public interface OnAnimatingListener {
        void onAnimating();
        void onDismiss();
    }

    public static class LoadingViewContainer {
        private ViewGroup parentView;
        private CardView innerRectangle;
        private RelativeLayout innerRelativeLayout;
        private RelativeLayout loadingLayoutContainer;
        private View loadingLayoutOutsideView;
        private View innerRectangleCover;
        private AVLoadingIndicatorView avLoadingIndicatorView;
        private RelativeLayout.LayoutParams animationLayoutParams;
        private RelativeLayout.LayoutParams textLayoutParams;
        private RelativeLayout.LayoutParams innerRectangleParams;
        private TextView loadingTextView;
        private int size;
        private int defaultAnimText = 20;
        private Activity mActivity;
        private long minAnimTime = 1000;
        private long maxAnimTime = 600000;
        private boolean isForcedDismiss = false;
        private boolean isSetToDismiss = false;
        private boolean isDismissed = false;
        private OnAnimatingListener animatingListener;

        public LoadingViewContainer(Activity activity) {
            this.mActivity = activity;
            this.parentView  = (ViewGroup)((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
            buildLayout();
        }

        @SuppressWarnings("ResourceType")
        private void buildLayout() {
            size = mActivity.getResources().getDisplayMetrics().widthPixels;
            // 主容器
            loadingLayoutContainer = new RelativeLayout(mActivity);
            // api21 版本才可使用此功能
            if (Build.VERSION.SDK_INT >= 21) {
                loadingLayoutContainer.setElevation(999f);
            }
            loadingLayoutContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 防止其它控件影响
                }
            });
            loadingLayoutContainer.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            // 外部背景控件
            loadingLayoutOutsideView = new View(mActivity);
            loadingLayoutOutsideView.setBackgroundColor(Color.parseColor("#000000"));
            loadingLayoutOutsideView.setAlpha(0.5f);
            loadingLayoutOutsideView.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            // 内部方形背景
            innerRectangle = new CardView(mActivity);
            innerRectangle.setCardElevation(0);
            innerRectangle.setCardBackgroundColor(Color.parseColor("#00000000"));
            innerRectangle.setRadius(30f);
            innerRectangleParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            innerRectangleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            innerRectangle.setLayoutParams(innerRectangleParams);
            // 内部方形RelativeLayout
            innerRelativeLayout = new RelativeLayout(mActivity);
            // 内部方形cover
            innerRectangleCover = new View(mActivity);
            innerRectangleCover.setBackgroundColor(Color.parseColor("#6B6969"));
            innerRectangleCover.setAlpha(0.8f);
            // 动画控件
            avLoadingIndicatorView = new AVLoadingIndicatorView(mActivity);
            animationLayoutParams = new RelativeLayout.LayoutParams(size / 7, size / 7);
            avLoadingIndicatorView.setId(1);
            avLoadingIndicatorView.setIndicator("BallSpinFadeLoaderIndicator");
            // 提示控件
            loadingTextView = new TextView(mActivity);
            textLayoutParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            loadingTextView.setMaxWidth(size / 3);
            loadingTextView.setTextColor(Color.parseColor("#ffffff"));
            loadingTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            loadingTextView.setText("loading");
            // 其他设置
            setShowInnerRectangle(false);
            setLoadingContentMargins(50);
        }

        public LoadingViewContainer setLoadingContentMargins(int margins) {
            // 加载动画控件
            animationLayoutParams.topMargin = margins;
            animationLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            // 字体控件
            textLayoutParams.leftMargin = margins;
            textLayoutParams.rightMargin = margins;
            textLayoutParams.bottomMargin = margins;
            textLayoutParams.topMargin = defaultAnimText;
            return this;
        }

        public LoadingViewContainer setLoadingContentMargins(int left, int top, int right, int bottom) {
            // 加载动画控件
            animationLayoutParams.topMargin = top;
            animationLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            // 字体控件
            textLayoutParams.leftMargin = left;
            textLayoutParams.rightMargin = right;
            textLayoutParams.bottomMargin = bottom;
            textLayoutParams.topMargin = defaultAnimText;
            return this;
        }

        public LoadingViewContainer setAnimationSize(double multiple) {
            if (multiple > 0) {
                animationLayoutParams.width = (int)(size * multiple);
                animationLayoutParams.height = (int)(size * multiple);
            }
            return this;
        }

        public LoadingViewContainer setAnimationSize(int width, int height) {
            if (width > 0 && height > 0) {
                animationLayoutParams.width = width;
                animationLayoutParams.height = height;
            }
            return this;
        }

        public LoadingViewContainer setLoadingContentMargins(int left, int top, int right, int bottom, int animTextMargin) {
            // 加载动画控件
            animationLayoutParams.topMargin = top;
            animationLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            // 字体控件
            textLayoutParams.leftMargin = left;
            textLayoutParams.rightMargin = right;
            textLayoutParams.bottomMargin = bottom;
            textLayoutParams.topMargin = animTextMargin;
            return this;
        }

        public LoadingViewContainer setInnerRectangleRadius(float radius) {
            if (radius >= 0) {
                innerRectangle.setRadius(radius);
            }
            return this;
        }

        public LoadingViewContainer setLoadingContentTopMargin(int top) {
            animationLayoutParams.topMargin = top;
            return this;
        }

        public LoadingViewContainer setLoadingContentLeftMargin(int left) {
            textLayoutParams.leftMargin = left;
            return this;
        }

        public LoadingViewContainer setLoadingContentRightMargin(int right) {
            textLayoutParams.rightMargin = right;
            return this;
        }

        public LoadingViewContainer setLoadingContentBottomMargin(int bottom) {
            textLayoutParams.bottomMargin = bottom;
            return this;
        }

        public LoadingViewContainer setAnimTextMargin(int margin) {
            textLayoutParams.topMargin = margin;
            return this;
        }

        public LoadingViewContainer setInnerRectangleColor(String color) {
            innerRectangleCover.setBackgroundColor(Color.parseColor(color));
            return this;
        }

        public LoadingViewContainer setInnerRectangleColor(int color) {
            innerRectangleCover.setBackgroundColor(color);
            return this;
        }

        public LoadingViewContainer setShowInnerRectangle(boolean showInnerRectangle) {
            if (!showInnerRectangle) {
                innerRectangleCover.setVisibility(View.INVISIBLE);
            } else {
                innerRectangleCover.setVisibility(View.VISIBLE);
            }
            return this;
        }

        public LoadingViewContainer setInnerRectangleAlpha(float alpha) {
            if (alpha >= 0 && alpha <= 1) {
                innerRectangleCover.setAlpha(alpha);
            }
            return this;
        }

        public LoadingViewContainer setHintTextMaxWidth(double val) {
            if (val > 0 && val <= 1) {
                loadingTextView.setMaxWidth((int) (size * val));
            }
            return this;
        }

        public LoadingViewContainer setHintTextColor(int color) {
            loadingTextView.setTextColor(color);
            return this;
        }

        public LoadingViewContainer setHintTextSize(int size) {
            loadingTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            return this;
        }

        public LoadingViewContainer setHintText(String msg) {
            loadingTextView.setText(msg);
            return this;
        }

        public LoadingViewContainer setOutsideAlpha(float alpha) {
            if (alpha >= 0 && alpha <= 1) {
                loadingLayoutOutsideView.setAlpha(alpha);
            }
            return this;
        }

        public LoadingViewContainer setOnTouchOutsideListener(View.OnClickListener clickListener) {
            loadingLayoutOutsideView.setOnClickListener(clickListener);
            return this;
        }

        public LoadingViewContainer setIndicator(String indicatorName) {
            avLoadingIndicatorView.setIndicator(indicatorName);
            return this;
        }

        public LoadingViewContainer setOnAnimatingListener(OnAnimatingListener animatingListener) {
            this.animatingListener = animatingListener;
            return this;
        }

        public LoadingViewContainer setTouchOutsideToDismiss(boolean isDismiss) {
            if (isDismiss) {
                loadingLayoutOutsideView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
            }
            return this;
        }

        public LoadingViewContainer setMaxAnimTime(long maxAnimTime) {
            if (maxAnimTime >= 0) {
                this.maxAnimTime = maxAnimTime;
            }
            return this;
        }

        public LoadingViewContainer setMinAnimTime(long minAnimTime) {
            if (minAnimTime >= 0) {
                this.minAnimTime = minAnimTime;
            }
            return this;
        }

        public void dismiss(boolean isForcedDismiss) {
            this.isForcedDismiss = isForcedDismiss;
            this.isSetToDismiss = true;
            if (isForcedDismiss) {
                dismiss();
            }
        }

        public boolean isShowing() {
            return this.isDismissed;
        }

        private void dismiss() {
            isDismissed = true;
            avLoadingIndicatorView.hide();
            parentView.removeView(loadingLayoutContainer);
            if (animatingListener != null) {
                animatingListener.onDismiss();
            }
        }

        /**
         * 开始构建
         */
        public void build() {
            animationLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            textLayoutParams.addRule(RelativeLayout.BELOW, avLoadingIndicatorView.getId());
            innerRelativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
            loadingTextView.setLayoutParams(textLayoutParams);
            avLoadingIndicatorView.setLayoutParams(animationLayoutParams);
            /**
             *  把子容器加到父容器中
             */
            // 1
            innerRelativeLayout.addView(avLoadingIndicatorView);
            innerRelativeLayout.addView(loadingTextView);
            // 2
            innerRectangle.addView(innerRelativeLayout);
            innerRectangle.measure(0, 0);
            innerRectangleCover.setLayoutParams(new RelativeLayout.LayoutParams(innerRectangle.getMeasuredWidth(), innerRectangle.getMeasuredHeight()));
            innerRectangle.addView(innerRectangleCover);
            innerRectangle.bringChildToFront(innerRelativeLayout);
            // 3
            loadingLayoutContainer.addView(loadingLayoutOutsideView);
            loadingLayoutContainer.addView(innerRectangle);
            // 设置
            loadingLayoutContainer.bringChildToFront(innerRectangle);
            avLoadingIndicatorView.show();
            // 4
            parentView.addView(loadingLayoutContainer);
            parentView.bringChildToFront(loadingLayoutContainer);
            parentView.updateViewLayout(loadingLayoutContainer, loadingLayoutContainer.getLayoutParams());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    long time = 0;
                    while (true) {
                        try {
                            if (time <= maxAnimTime && !isForcedDismiss) {
                                if (animatingListener != null) {
                                    animatingListener.onAnimating();
                                }
                                if (time >= minAnimTime && isSetToDismiss) {
                                    mHandler.sendEmptyMessage(0);
                                    break;
                                }
                            } else {
                                break;
                            }
                            time += 100;
                            Thread.sleep(100);
                        } catch (Exception e) {
                            Log.d("测试", "动画出错run: " + e.toString());
                        }
                    }
                    mHandler.sendEmptyMessage(5);
                }
            }).start();
        }

        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (!isDismissed) {
                    dismiss();
                }
            }
        };
    }
}
