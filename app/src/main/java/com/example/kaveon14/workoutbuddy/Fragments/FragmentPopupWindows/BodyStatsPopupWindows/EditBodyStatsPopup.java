package com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.BodyStatsPopupWindows;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Body;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.BodyTable;
import com.example.kaveon14.workoutbuddy.Fragments.FragmentPopupWindows.PopupWindowManager;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.BodyStatsFragment;
import com.example.kaveon14.workoutbuddy.R;


public class EditBodyStatsPopup extends PopupWindowManager {

    private boolean updatingRow = false;

    public EditBodyStatsPopup(View root, Context context) {
        setRootView(root);
        setWindowManagerContext(context);
        setPopupLayout(R.layout.editbodystats_popup_layout);
        setPopupViewId(R.id.editBodyStats_popup);
    }

    public void showPopupWindow() {
        displayPopupWindow();
        Button btn = (Button) popupLayout.findViewById(R.id.addStats_btnPop);
        if(updatingRow) {
            setEditTextView();
            btn.setText(R.string.editedBodyStats);
        } else {
            btn.setText(R.string.addStats);
        }
        addButton();
    }

    public void isUpdatingRow(boolean updatingRow) {
        this.updatingRow = updatingRow;
    }


    private void setEditTextView() {
        EditBodyStatsPopup.BodyStatsExtension bodyStatsExtension
                = new EditBodyStatsPopup.BodyStatsExtension();
        Body body = BodyStatsFragment.getClickedBodyStatsItem();

        bodyStatsExtension.setDate(body.getStringDate(),popupLayout);
        bodyStatsExtension.setWeight(body.getWeight(),popupLayout);
        bodyStatsExtension.setChestSize(body.getChestSize(),popupLayout);

        bodyStatsExtension.setBackSize(body.getBackSize(),popupLayout);
        bodyStatsExtension.setArmSize(body.getArmSize(),popupLayout);
        bodyStatsExtension.setForearmSize(body.getForearmSize(),popupLayout);

        bodyStatsExtension.setWaistSize(body.getWaistSize(),popupLayout);
        bodyStatsExtension.setQuadSize(body.getQuadSize(),popupLayout);
        bodyStatsExtension.setCalfSize(body.getCalfSize(),popupLayout);
    }

    private void addBodyStatsData() {
        EditBodyStatsPopup.BodyStatsExtension bodyStatsExtension =
                new EditBodyStatsPopup.BodyStatsExtension();
        bodyStatsExtension.addBodyStatsData(popupLayout);
    }

    private void addButton() {
        Button btn = (Button) popupLayout.findViewById(R.id.addStats_btnPop);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Body body = new EditBodyStatsPopup.BodyStatsExtension()
                        .getBodyStatsObject(popupLayout);
                if (body != null) {
                    addBodyStatsData();
                    BodyStatsFragment.setNewBodyStats(body);
                    Toast.makeText(context, "Stats Successfully Added!",
                            Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                }
            }
        });
    }

    private class BodyStatsExtension {

        private void addBodyStatsData(View root) {
            new BodyTable(context).addStatsToBodyTable(getBodyStatsObject(root));
        }

        private Body getBodyStatsObject(View root) {
            Body body = new Body().setWeight(getWeight(root))
                    .setChestSize(getChestSize(root)).setBackSize(getBackSize(root))
                    .setArmSize(getArmSize(root)).setForearmSize(getForearmSize(root))
                    .setWaistSize(getWaistSize(root)).setQuadSize(getQuadSize(root))
                    .setCalfSize(getCalfSize(root));
            String date = getDate(root);
            try {
                body.setDate(date);
            } catch (IllegalArgumentException e) {
                Toast.makeText(context,"Date Not Entered Properly" +
                        ", Data NOT Saved!!",Toast.LENGTH_LONG).show();
                return null;
            }

            return body;
        }

        private String getDate(View root) {
            EditText date = (EditText) root.findViewById(R.id.date_editText);
            return date.getText().toString();
        }

        private String getWeight(View root) {
            EditText weight = (EditText) root.findViewById(R.id.weight_editText);
            return weight.getText().toString();
        }

        private String getChestSize(View root) {
            EditText chestSize = (EditText) root.findViewById(R.id.chest_editText);
            return chestSize.getText().toString();
        }

        private String getBackSize(View root) {
            EditText backSize = (EditText) root.findViewById(R.id.back_editText);
            return backSize.getText().toString();
        }

        private String getArmSize(View root) {
            EditText armSize = (EditText) root.findViewById(R.id.arms_editText);
            return armSize.getText().toString();
        }

        private String getForearmSize(View root) {
            EditText forearmSize = (EditText) root.findViewById(R.id.forearms_editText);
            return forearmSize.getText().toString();
        }

        private String getWaistSize(View root) {
            EditText waistSize = (EditText) root.findViewById(R.id.waist_editText);
            return waistSize.getText().toString();
        }

        private String getQuadSize(View root) {
            EditText quadSize = (EditText) root.findViewById(R.id.quads_editText);
            return quadSize.getText().toString();
        }

        private String getCalfSize(View root) {
            EditText calfSize = (EditText) root.findViewById(R.id.calfs_editText);
            return calfSize.getText().toString();
        }

        private void setDate(String date,View root) {
            EditText dateView = (EditText) root.findViewById(R.id.date_editText);
            dateView.setText(date);
        }

        private void setWeight(String weight,View root) {
            EditText weightView = (EditText) root.findViewById(R.id.weight_editText);
            weightView.setText(weight);
        }

        private void setChestSize(String size,View root) {
            EditText chestView = (EditText) root.findViewById(R.id.chest_editText);
            chestView.setText(size);
        }

        private void setBackSize(String size,View root) {
            EditText backView = (EditText) root.findViewById(R.id.back_editText);
            backView.setText(size);
        }

        private void setArmSize(String size,View root) {
            EditText armView = (EditText) root.findViewById(R.id.arms_editText);
            armView.setText(size);
        }

        private void setForearmSize(String size, View root) {
            EditText forearmView = (EditText) root.findViewById(R.id.forearms_editText);
            forearmView.setText(size);
        }

        private void setWaistSize(String size,View root) {
            EditText waistView = (EditText) root.findViewById(R.id.waist_editText);
            waistView.setText(size);
        }

        private void setQuadSize(String size,View root) {
            EditText quadView = (EditText) root.findViewById(R.id.quads_editText);
            quadView.setText(size);
        }

        private void setCalfSize(String size,View root) {
            EditText calfView = (EditText) root.findViewById(R.id.calfs_editText);
            calfView.setText(size);
        }
    }
}
