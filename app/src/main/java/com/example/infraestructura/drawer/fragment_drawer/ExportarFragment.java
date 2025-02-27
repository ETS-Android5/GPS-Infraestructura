package com.example.infraestructura.drawer.fragment_drawer;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.infraestructura.Infra_Ave.AveBD;
import com.example.infraestructura.Infra_Engorda.EngordaBD;
import com.example.infraestructura.Infra_Granos.GranosBD;
import com.example.infraestructura.Infra_Lecheros.LecherosBD;
import com.example.infraestructura.Infra_Porcino.PorcinoBD;
import com.example.infraestructura.Infra_Sacrificio.SacrificioBD;
import com.example.infraestructura.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExportarFragment extends Fragment {



    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1 ;
    private TextView tvProgreso;

    ExtendedFloatingActionButton fabExportar, aaaa;
    TextView ed1, ed2, ed3, ed4, ed5, ed6, ed7, ed8, ed9, ed10, ed11;
    private ProgressDialog progressDialog;

    public ExportarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vistaExportar= inflater.inflate(R.layout.fragment_exportar, container, false);


        // tvProgreso = vistaExportar.findViewById(R.id.tvProgreso);
        fabExportar = vistaExportar.findViewById(R.id.fab_exportar);
        aaaa = vistaExportar.findViewById(R.id.aaa);
        ed1 = vistaExportar.findViewById(R.id.pasl_o_tvTitulo55);
        ed1.setVisibility(View.GONE);

        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        }

        aaaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Estas segur@?")
                        .setContentText("Deseas borrar tus registros")
                        .setConfirmText("Si")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                SacrificioBD baseBD;
                                baseBD = new SacrificioBD(getContext());
                                baseBD.deleteSacrificio();

                                EngordaBD baseBD2;
                                baseBD2 = new EngordaBD(getContext());
                                baseBD2.deleteEngorda();

                                PorcinoBD baseBD3;
                                baseBD3 = new PorcinoBD(getContext());
                                baseBD3.deletePorcino();

                                LecherosBD baseBD4;
                                baseBD4 = new LecherosBD(getContext());
                                baseBD4.deleteLecheros();

                                AveBD baseBD5;
                                baseBD5 = new AveBD(getContext());
                                baseBD5.deleteAve();

                                GranosBD baseBD6;
                                baseBD6 = new GranosBD(getContext());
                                baseBD6.deleteGranos();


                                ed1.setText("Bases de datos borradas.");
                                ed1.setVisibility(View.VISIBLE);
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();


            }

        });


        fabExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed1.setVisibility(View.GONE);
                ed1.setText("Listo!, Bases de datos exportadas.");
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Descargando Datos");
                progressDialog.setCancelable(false);
                //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.setMax(10);
                progressDialog.setMessage("Descargando...");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        try {
                            deployDatabase("CentrosdeSacrificio");
                        } catch (IOException e) {
                            try {
                                deployDatabase("CentrosdeSacrificio");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            e.printStackTrace();

                        }

                        try {
                            deployDatabase("CorralesdeEngorda");
                        } catch (IOException e) {
                            try {
                                deployDatabase("CorralesdeEngorda");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            e.printStackTrace();

                        }

                        try {
                            deployDatabase("GranjasdePorcino");
                        } catch (IOException e) {
                            try {
                                deployDatabase("GranjasdePorcino");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            e.printStackTrace();

                        }

                        try {
                            deployDatabase("EstablosLecheros");
                        } catch (IOException e) {
                            try {
                                deployDatabase("EstablosLecheros");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            e.printStackTrace();

                        }

                        try {
                            deployDatabase("GranjasdeAve");
                        } catch (IOException e) {
                            try {
                                deployDatabase("GranjasdeAve");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            e.printStackTrace();

                        }

                        try {
                            deployDatabase("AlmacenesdeGranos");
                        } catch (IOException e) {
                            try {
                                deployDatabase("AlmacenesdeGranos");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            e.printStackTrace();

                        }


                        ed1.setVisibility(View.VISIBLE);
                    }
                },2000);





                // TareaAsyncTask tareaAsyncTask = new TareaAsyncTask();
                // tareaAsyncTask.execute();
                progressDialog.dismiss();
            }

        });

        return vistaExportar;
    }

    private class TareaAsyncTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute(){
            //tvProgreso.setText("0");
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Exportando Datos");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(10);
            progressDialog.setMessage("Exportando");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {



            for(int i=1; i<=10; i++){
                esperarUnSegundo();
                //publishProgress(i * 100 / 100);
                publishProgress(i);
            }



            //publishProgress(100 * 100 / 100);
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            //tvProgreso.setText(values[0].toString());
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean result){
            //tvProgreso.setText(resultado);
            progressDialog.dismiss();
        }
    }

    private void esperarUnSegundo(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){

        }

    }

    public File crearDirectorioPublico(String nombreDirectorio) {
        //Crear directorio público en la carpeta Pictures.
        File directorio = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), nombreDirectorio);
        //Muestro un mensaje en el logcat si no se creo la carpeta por algun motivo

        return directorio;
    }


    private void deployDatabase(String DB_NAME) throws IOException {
//Open your local db as the input stream

       /* if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
        }
*/
        String packageName = getContext().getPackageName();
        String DB_PATH = "/data/data/" + packageName + "/databases/";
        String DB_PATHCopy =Environment.getExternalStorageDirectory() + "/" + DB_NAME;

        String version = Build.VERSION.RELEASE;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            File file = new File(getActivity().getExternalFilesDir(null), "/" + DB_NAME + "/");
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        else{
            File directory = new File(DB_PATHCopy);
            if (!directory.exists()) {
                //Toast.makeText(getContext(), "No: " + directory, Toast.LENGTH_LONG).show();
                String direc = Environment.getExternalStorageDirectory().toString() + "/" + DB_NAME + "/";

                new File(direc).mkdirs();
            }
            else{
                //Toast.makeText(getContext(), "Si: " + directory, Toast.LENGTH_LONG).show();
            }
        }




        Log.e("========>>", DB_PATH);

        //InputStream myInput = getAssets().open("BDENCUESTA");
        InputStream myInput = new FileInputStream(DB_PATH + DB_NAME);

// Path to the just created empty db
        //String outFileName = DB_PATH + DB_NAME;
        //String outFileName = Environment.getExternalStorageDirectory() + "/" + DB_NAME;

        String outFileName;
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            File file = new File(getActivity().getExternalFilesDir(null), "/" + DB_NAME + "/" + DB_NAME);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            outFileName = file.getAbsolutePath();
        }
        else{
            outFileName = Environment.getExternalStorageDirectory() + "/" + DB_NAME + "/" + DB_NAME;
        }

        //String outFileName = Environment.getExternalStorageDirectory() + "/" + "GEOESPACIALES" + "/" + "basedatos";

        Log.e("========>>", outFileName);
//Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

//transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

//Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();


    }





}