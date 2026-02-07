package com.gymkhana.studio.library.widget;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;
import android.util.*;

@SuppressWarnings("all")
public class ProgressStudio extends AlertDialog {

	private DialogActionListener dialogActionListener;
	private Drawable baseIconDefault;

	private boolean hide = false;
	private boolean hideSpin = false;
	private boolean isGradient = false;
	
	private LinearLayout root;
	private ProgressBar progressSpin;
	private LinearLayout layout;
	private TextView label;
	private ProgressBar pb;
	private LinearLayout rootLayout;
	private TextView tvOne;
	private LinearLayout spacer;
	private TextView tvTwo;
	
    public ProgressStudio(Context context) {
        super(context);
        initialize();
    }

	public ProgressStudio(Context context, int theme) {
        super(context, theme);
        initialize();
    }
	private void initialize() {
		root = new LinearLayout(getContext());
		progressSpin = new ProgressBar(getContext());
		layout = new LinearLayout(getContext());
		label = new TextView(getContext());
		pb = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
		rootLayout = new LinearLayout(getContext());
		tvOne = new TextView(getContext());
		spacer = new LinearLayout(getContext());
		tvTwo = new TextView(getContext());
		baseIconDefault = getContext().getDrawable(R.drawable.ic_alien);
	}
	
    private void initialize2() {
		
		LinearLayout.LayoutParams lroot = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lroot.setMargins(10, 10, 10, 10);
		root.setLayoutParams(lroot);
		root.setOrientation(LinearLayout.HORIZONTAL);
		root.setPadding(20, 20, 20, 20);

		
		LinearLayout.LayoutParams lprogSpin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lprogSpin.setMargins(10, 25, 0, 10);
		progressSpin.setPadding(0, 0, 20, 0);
		progressSpin.setIndeterminate(true);
		progressSpin.setLayoutParams(lprogSpin);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 25, 10, 10);
		layout.setLayoutParams(lp);
		layout.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		textParam.setMargins(5, 5, 5, 5);
		label.setLayoutParams(textParam);
		label.setSingleLine(true);
		label.setMinWidth(400);

		int dpValue = 8;
		int pixelValue = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP, 
			dpValue, 
			getContext().getResources().getDisplayMetrics()
		);
		LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, isGradient == true ? pixelValue : LinearLayout.LayoutParams.WRAP_CONTENT);
		progressParams.setMargins(5, 5, 5, 5);
		pb.setLayoutParams(progressParams);
		pb.setMax(100);
		
		// 1. Create the Root LinearLayout
		rootLayout.setOrientation(LinearLayout.HORIZONTAL);
		rootLayout.setGravity(Gravity.CENTER_VERTICAL);
		if (!isGradient) {
			rootLayout.setTranslationY((8 * getContext().getResources().getDisplayMetrics().density));
		}
		rootLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		// 2. Create the first TextView ("1/1")
		//tvOne.setText("");
		int padding = (int) (4 * getContext().getResources().getDisplayMetrics().density);
		tvOne.setPadding(padding, padding, padding, padding);
		tvOne.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		// 3. Create the Spacer LinearLayout (weight="1.0")
		spacer.setOrientation(LinearLayout.HORIZONTAL);
		LinearLayout.LayoutParams spacerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f); // weight = 1.0
		spacer.setLayoutParams(spacerParams);

		// 4. Create the second TextView ("90%")
		//tvTwo.setText("");
		tvTwo.setPadding(padding, padding, padding, padding);
		tvTwo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		// 5. Assemble the hierarchy
		rootLayout.addView(tvOne);
		rootLayout.addView(spacer);
		rootLayout.addView(tvTwo);

		layout.addView(label);
		if (hide) {
			layout.removeView(rootLayout);
		} else {
			layout.addView(rootLayout);
		}
		layout.addView(pb);

		if (hideSpin) {
			root.removeView(progressSpin);
		} else {
			root.addView(progressSpin);
		}
		root.addView(layout);
		setView(root);

		pb.setProgress(0);
		pb.setSecondaryProgress(0);
    }

	
	@Override
	public void setMessage(CharSequence message) {
		label.post(() -> {
			label.setText(message.toString());
		});
	}

	public void setProgress(int progress) {
		pb.setProgress(progress);
	}
	
	public void setProgress(int progress, int i, int total) {
		pb.setProgress(progress);
		if (!hide) {
			tvOne.post(() -> {
				tvOne.setText(i + "/" + total);
			});
		}
	}
	
	public void setHiddePercentage(boolean b) {
		hide = b;
	}
	
	public void setSecondaryProgress(int progress) {
		pb.setSecondaryProgress(progress);
		if (!hide) {
			tvTwo.post(() -> {
				tvTwo.setText(progress + "%");
			});
		}
	}

	public void setPrimaryColor(int color) {
		if (!isGradient) {
			LayerDrawable progressDrawable = (LayerDrawable) pb.getProgressDrawable();
			Drawable primaryProgressDrawable = progressDrawable.findDrawableByLayerId(android.R.id.progress);
			if (primaryProgressDrawable != null) {
				primaryProgressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
			}
		}
	}

	public void setSecondaryColor(int color) {
		if (!isGradient) {
			LayerDrawable progressDrawable = (LayerDrawable) pb.getProgressDrawable();
			Drawable secondaryProgressDrawable = progressDrawable.findDrawableByLayerId(android.R.id.secondaryProgress);
			if (secondaryProgressDrawable != null) {
				secondaryProgressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
			}
		}
	}

	public void setBackgroundProgress(int color) {
		if (!isGradient) {
			LayerDrawable progressDrawable = (LayerDrawable) pb.getProgressDrawable();
			Drawable backgroundDrawable = progressDrawable.findDrawableByLayerId(android.R.id.background);
			if (backgroundDrawable != null) {
				backgroundDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
			}
		}
	}

	public void setGradient(boolean b) {
		isGradient = b;
		if (b) {
			a();
		}
	}
	
	public void hideSpin(boolean _hide) {
		hideSpin = _hide;
	}
	
	public void setCustomSpin(boolean b, Drawable baseIcon) {
		if (b & !hideSpin) {
			//Drawable baseIcon = getContext().getDrawable(R.drawable.ic_launcher);
			Bitmap spinnerBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.spinner_outer);
			BitmapDrawable bitmapDrawable = new BitmapDrawable(getContext().getResources(), spinnerBitmap);
			bitmapDrawable.setAntiAlias(true);
			bitmapDrawable.setFilterBitmap(true);

			RotateDrawable rotateDrawable = new RotateDrawable();
			rotateDrawable.setDrawable(bitmapDrawable);
			rotateDrawable.setFromDegrees(0f);
			rotateDrawable.setToDegrees(360f);
			rotateDrawable.setPivotX(0.5f); // 50%
			rotateDrawable.setPivotY(0.5f); // 50%

			Drawable[] layers = new Drawable[2];
			layers[0] = (baseIcon != null ? baseIcon : baseIconDefault);
			layers[1] = rotateDrawable;
			LayerDrawable bb = new LayerDrawable(layers);
			progressSpin.setIndeterminateDrawable(bb);
		}
	}

	private void a() {
		// 1. Definisikan warna gradien untuk masing-masing bagian
		int[] backgroundColors = {Color.LTGRAY, Color.GRAY};
		int[] secondaryColors = {Color.parseColor("#90CAF9"), Color.parseColor("#42A5F5")}; // Biru muda
		//int[] primaryColors2 = {Color.parseColor("#FF5252"), Color.parseColor("#D32F2F")};   // Merah
		int[] primaryColors = {
			Color.parseColor("#FF071BF7"), // Start color (e.g., green)
			Color.parseColor("#FF0AC253"), // Middle color (e.g., light green)
			Color.parseColor("#FFFF7B08"), // End color (e.g., lime green)
			Color.parseColor("#FFFD1D1D"),
			Color.parseColor("#FF630606")
		};
		
		// 2. Buat GradientDrawable untuk tiap layer
		GradientDrawable bgGrad = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, backgroundColors);
		bgGrad.setCornerRadius(10f);

		GradientDrawable secondaryGrad = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, secondaryColors);
		secondaryGrad.setCornerRadius(10f);

		GradientDrawable primaryGrad = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, primaryColors);
		primaryGrad.setCornerRadius(10f);
		
		// 3. Bungkus progress utama dan sekunder dalam ClipDrawable
		ClipDrawable secondaryClip = new ClipDrawable(secondaryGrad, Gravity.LEFT, ClipDrawable.HORIZONTAL);
		ClipDrawable primaryClip = new ClipDrawable(primaryGrad, Gravity.LEFT, ClipDrawable.HORIZONTAL);
		
		// 4. Masukkan ke dalam LayerDrawable
		Drawable[] layers = {bgGrad, secondaryClip, primaryClip};
		LayerDrawable layerDrawable = new LayerDrawable(layers);
		
		// 5. Atur ID agar sistem mengenali fungsinya masing-masing
		layerDrawable.setId(0, android.R.id.background);
		layerDrawable.setId(1, android.R.id.secondaryProgress);
		layerDrawable.setId(2, android.R.id.progress);
		
		
		//tinggi lama
		Rect bounds = pb.getProgressDrawable().getBounds();
		pb.getProgressDrawable().setBounds(bounds);
		
		// 6. Terapkan ke ProgressBar
		layerDrawable.setBounds(bounds);
		pb.setProgressDrawable(layerDrawable);
	}

	private void setClickListener(View... views){
        for (View view: views){
            view.setOnClickListener(mOnClickListener);
        }
	}

	private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialogActionListener.onAction(v);
        }
	};

	public interface DialogActionListener{
        void onAction(View viewId);
	}

	public void setOnActionListener(DialogActionListener dialogActionListener){
		this.dialogActionListener = dialogActionListener;
	}

	@Override
	public void show() {
		initialize2();
		super.show();
	}
	
}
