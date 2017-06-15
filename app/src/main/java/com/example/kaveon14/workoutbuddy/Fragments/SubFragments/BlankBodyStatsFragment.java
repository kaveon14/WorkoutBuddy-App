package com.example.kaveon14.workoutbuddy.Fragments.SubFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.DataBase.Data.Body;
import com.example.kaveon14.workoutbuddy.DataBase.TableManagers.BodyTable;
import com.example.kaveon14.workoutbuddy.Fragments.MainFragments.BodyStatsFragment;
import com.example.kaveon14.workoutbuddy.R;
// TODO if no data added change data added to all zeros and throw error requesting a date
public class BlankBodyStatsFragment extends Fragment {

    private boolean updatingRow = false;

    public BlankBodyStatsFragment() {
        // Required empty public constructor
    }

    public void isUpdatingRow(boolean updatingRow) {
        this.updatingRow = updatingRow;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_blank_body_stats, container, false);
        addButton(root);
        if(updatingRow) {
            setEditTextView(root);
        }
        return root;
    }

    private void setEditTextView(View root) {//used if editing a bodystats item
        BodyStatsExtension bodyStatsExtension = new BodyStatsExtension();
        Body body = BodyStatsFragment.clickedBodyStatsItem;

        bodyStatsExtension.setDate(body.getStringDate(),root);
        bodyStatsExtension.setWeight(body.getWeight(),root);
        bodyStatsExtension.setChestSize(body.getChestSize(),root);

        bodyStatsExtension.setBackSize(body.getBackSize(),root);
        bodyStatsExtension.setArmSize(body.getArmSize(),root);
        bodyStatsExtension.setForearmSize(body.getForearmSize(),root);

        bodyStatsExtension.setWaistSize(body.getWaistSize(),root);
        bodyStatsExtension.setQuadSize(body.getQuadSize(),root);
        bodyStatsExtension.setCalfSize(body.getCalfSize(),root);
    }

    public void addBodyStatsData() {
        BodyStatsExtension bodyStatsExtension = new BodyStatsExtension();
        bodyStatsExtension.addBodyStatsData(getView());
    }

    private void addButton(View root) {
        Button btn = (Button) root.findViewById(R.id.addStats_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBodyStatsData();
                BodyStatsFragment.bb = new BodyStatsExtension()
                        .getBodyStatsObject(root);
                Toast.makeText(getContext(),"Stats Successfully Added!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class BodyStatsExtension {//create a body object

        private void addBodyStatsData(View root) {
            BodyTable bodyTable = new BodyTable(getContext());
            bodyTable.addStatsToBodyTable(getDate(root),getWeight(root),getChestSize(root),
                    getBackSize(root),getArmSize(root),getForearmSize(root),getWaistSize(root),
                    getQuadSize(root),getCalfSize(root));
        }

        private Body getBodyStatsObject(View root) {
            return new Body().setDate(getDate(root)).setWeight(getWeight(root))
                    .setChestSize(getChestSize(root)).setBackSize(getBackSize(root))
                    .setArmSize(getArmSize(root)).setForearmSize(getForearmSize(root))
                    .setWaistSize(getWaistSize(root)).setQuadSize(getQuadSize(root))
                    .setCalfSize(getCalfSize(root));
        }

        private String getDate(View root) {
            EditText date = (EditText) root.findViewById(R.id.date_inputTextView);
            return date.getText().toString();
        }

        private String getWeight(View root) {
            EditText weight = (EditText) root.findViewById(R.id.weight_inputTextView);
            return weight.getText().toString();
        }

        private String getChestSize(View root) {
            EditText chestSize = (EditText) root.findViewById(R.id.chest_inputTextView);
            return chestSize.getText().toString();
        }

        private String getBackSize(View root) {
            EditText backSize = (EditText) root.findViewById(R.id.back_inputTextView);
            return backSize.getText().toString();
        }

        private String getArmSize(View root) {
            EditText armSize = (EditText) root.findViewById(R.id.arm_inputTextView);
            return armSize.getText().toString();
        }

        private String getForearmSize(View root) {
            EditText forearmSize = (EditText) root.findViewById(R.id.forearms_inputTextView);
            return forearmSize.getText().toString();
        }

        private String getWaistSize(View root) {
            EditText waistSize = (EditText) root.findViewById(R.id.waist_inputTextView);
            return waistSize.getText().toString();
        }

        private String getQuadSize(View root) {
            EditText quadSize = (EditText) root.findViewById(R.id.quad_inputTextView);
            return quadSize.getText().toString();
        }

        private String getCalfSize(View root) {
            EditText calfSize = (EditText) root.findViewById(R.id.calf_inputTextView);
            return calfSize.getText().toString();
        }

        private void setDate(String date,View root) {
            EditText dateView = (EditText) root.findViewById(R.id.date_inputTextView);
            dateView.setText(date);
        }

        private void setWeight(String weight,View root) {
            EditText weightView = (EditText) root.findViewById(R.id.weight_inputTextView);
            weightView.setText(weight);
        }

        private void setChestSize(String size,View root) {
            EditText chestView = (EditText) root.findViewById(R.id.chest_inputTextView);
            chestView.setText(size);
        }

        private void setBackSize(String size,View root) {
            EditText backView = (EditText) root.findViewById(R.id.back_inputTextView);
            backView.setText(size);
        }

        private void setArmSize(String size,View root) {
            EditText armView = (EditText) root.findViewById(R.id.arm_inputTextView);
            armView.setText(size);
        }

        private void setForearmSize(String size, View root) {
            EditText forearmView = (EditText) root.findViewById(R.id.forearms_inputTextView);
            forearmView.setText(size);
        }

        private void setWaistSize(String size,View root) {
            EditText waistView = (EditText) root.findViewById(R.id.waist_inputTextView);
            waistView.setText(size);
        }

        private void setQuadSize(String size,View root) {
            EditText quadView = (EditText) root.findViewById(R.id.quad_inputTextView);
            quadView.setText(size);
        }

        private void setCalfSize(String size,View root) {
            EditText calfView = (EditText) root.findViewById(R.id.calf_inputTextView);
            calfView.setText(size);
        }
    }
}
