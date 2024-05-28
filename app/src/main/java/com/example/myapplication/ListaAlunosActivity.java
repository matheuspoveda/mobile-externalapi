package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.AlunoAdapter;
import com.example.myapplication.model.Aluno;
import com.example.myapplication.network.AlunoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAlunos;
    private AlunoService alunoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        recyclerViewAlunos = findViewById(R.id.recyclerViewAlunos);
        recyclerViewAlunos.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://665524063c1d3b6029385b96.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        alunoService = retrofit.create(AlunoService.class);

        carregarAlunos();
    }

    private void carregarAlunos() {
        Call<List<Aluno>> call = alunoService.getAlunos();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                if (response.isSuccessful()) {
                    List<Aluno> alunos = response.body();
                    recyclerViewAlunos.setAdapter(new AlunoAdapter(alunos));
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                // erro imaginario
            }
        });
    }
}
