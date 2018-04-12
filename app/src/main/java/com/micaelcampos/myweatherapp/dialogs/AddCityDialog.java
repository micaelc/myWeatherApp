package com.micaelcampos.myweatherapp.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.micaelcampos.myweatherapp.R;

public class AddCityDialog extends DialogFragment {
	public interface AddCityDialogListner {
		void onDialogPositiveClick(DialogFragment dialog, String cityName);

		void onDialogNegativeClick(DialogFragment dialog);
	}

	AddCityDialogListner mDialogListner;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the host
			mDialogListner = (AddCityDialogListner) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement AddCityDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View view = inflater.inflate(R.layout.add_city_dialog, null);
		builder.setView(view)
				.setPositiveButton(R.string.dialog_button_add, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						EditText etCityName = (EditText) view.findViewById(R.id.etCityName);
						mDialogListner.onDialogPositiveClick(AddCityDialog.this, etCityName.getText().toString());
					}
				})
				.setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						mDialogListner.onDialogNegativeClick(AddCityDialog.this);
					}
				});

		return builder.create();

	}
}
