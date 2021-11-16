package com.example.exampleproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FragmentContract extends Fragment implements OnPageScrollListener {

    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.buttonConfirm)
    Button confirmBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_contract, container, false);
        ButterKnife.bind(this, view);

        pdfView.fromAsset("sozlesmeTest.pdf").onPageScroll(this).load();
        return view;
    }

    @OnClick(R.id.buttonConfirm)
    public void acceptButtonClick() {
        Bundle bundle = new Bundle();
        bundle.putInt("contractAccept", 1);

        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("createFormFragment");
        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        confirmBtn.setVisibility(View.VISIBLE);
        confirmBtn.setEnabled(false);
        confirmBtn.setAlpha(.5f);
    }

    @Override
    public void onPageScrolled(int page, float positionOffset) {
        if (pdfView != null) {
            if (pdfView.getPageCount() - 1 == page && positionOffset == 1.0f) {
                if (confirmBtn != null) {
                    confirmBtn.setEnabled(true);
                    confirmBtn.setAlpha(1f);
                }
            } else if (pdfView.getPageCount() - 1 == 0) {
                if (confirmBtn != null) {
                    confirmBtn.setEnabled(true);
                    confirmBtn.setAlpha(1f);
                }
            }
        }
    }

}