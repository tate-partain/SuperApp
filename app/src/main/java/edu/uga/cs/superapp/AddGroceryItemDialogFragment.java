package edu.uga.cs.superapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

// A DialogFragment class to handle grocery item additions from the grocery list review activity
// It uses a DialogFragment to allow the input of a new grocery item.
public class AddGroceryItemDialogFragment extends DialogFragment{

    private EditText itemNameView;
    private EditText priceView;
//    private EditText urlView;
//    private EditText commentsView;

    // This interface will be used to obtain the new grocery item from an AlertDialog.
    // A class implementing this interface will handle the new item, i.e. store it
    // in Firebase and add it to the RecyclerAdapter.
    public interface AddGroceryItemDialogListener {
        void onFinishNewGroceryItemDialog(GroceryItem groceryItem);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create the AlertDialog view
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.add_grocery_item_dialog,
                (ViewGroup) getActivity().findViewById(R.id.root));

        // get the view objects in the AlertDialog
        itemNameView = layout.findViewById( R.id.editText1 );
        priceView = layout.findViewById( R.id.editText2 );
//        urlView = layout.findViewById( R.id.editText3 );
//        commentsView = layout.findViewById( R.id.editText4 );

        // create a new AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);  //Todo: create a style for this or find a new one. May have done something weird when creating this
        // Set its view (inflated above).
        builder.setView(layout);

        // Set the title of the AlertDialog
        builder.setTitle( "New Grocery Item" );
        // Provide the negative button listener
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });
        // Provide the positive button listener
        builder.setPositiveButton( android.R.string.ok, new ButtonClickListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    private class ButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String itemName = itemNameView.getText().toString();
            String price = priceView.getText().toString();
//            String url = urlView.getText().toString();
//            String comments = commentsView.getText().toString();
            GroceryItem groceryItem = new GroceryItem( itemName, price);

            // get the Activity's listener to add the new job lead
            AddGroceryItemDialogListener listener = (AddGroceryItemDialogListener) getActivity();
            // add the new job lead
            listener.onFinishNewGroceryItemDialog( groceryItem );
            // close the dialog
            dismiss();
        }
    }
}
