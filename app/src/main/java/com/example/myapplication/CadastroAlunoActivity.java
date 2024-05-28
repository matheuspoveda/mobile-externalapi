package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.Aluno;
import com.example.myapplication.model.Endereco;
import com.example.myapplication.network.AlunoService;
import com.example.myapplication.network.ViaCepService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroAlunoActivity extends AppCompatActivity {

    private EditText editTextRa, editTextNome, editTextCep, editTextLogradouro, editTextComplemento, editTextBairro, editTextCidade, editTextUf;
    private Button buttonBuscarCep, buttonCadastrarAluno;
    private ViaCepService viaCepService;
    private AlunoService alunoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        editTextRa = findViewById(R.id.editTextRa);
        editTextNome = findViewById(R.id.editTextNome);
        editTextCep = findViewById(R.id.editTextCep);
        editTextLogradouro = findViewById(R.id.editTextLogradouro);
        editTextComplemento = findViewById(R.id.editTextComplemento);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextUf = findViewById(R.id.editTextUf);
        buttonBuscarCep = findViewById(R.id.buttonBuscarCep);
        buttonCadastrarAluno = findViewById(R.id.buttonCadastrarAluno);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        viaCepService = retrofit.create(ViaCepService.class);

        Retrofit retrofitMockApi = new Retrofit.Builder()
                .baseUrl("https://665524063c1d3b6029385b96.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        alunoService = retrofitMockApi.create(AlunoService.class);

        buttonBuscarCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cep = editTextCep.getText().toString();
                buscarEndereco(cep);
            }
        });

        buttonCadastrarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarAluno();
            }
        });
    }

    private void buscarEndereco(String cep) {
        Call<Endereco> call = viaCepService.getEndereco(cep);
        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful()) {
                    Endereco endereco = response.body();
                    if (endereco != null) {
                        editTextLogradouro.setText(endereco.getLogradouro());
                        editTextComplemento.setText(endereco.getComplemento());
                        editTextBairro.setText(endereco.getBairro());
                        editTextCidade.setText(endereco.getLocalidade());
                        editTextUf.setText(endereco.getUf());
                    }
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(CadastroAlunoActivity.this, "Erro ao buscar endereço", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cadastrarAluno() {
        int ra = Integer.parseInt(editTextRa.getText().toString());
        String nome = editTextNome.getText().toString();
        String cep = editTextCep.getText().toString();
        String logradouro = editTextLogradouro.getText().toString();
        String complemento = editTextComplemento.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String cidade = editTextCidade.getText().toString();
        String uf = editTextUf.getText().toString();

        Aluno aluno = new Aluno(ra, nome, cep, logradouro, complemento, bairro, cidade, uf);

        Call<Aluno> call = alunoService.createAluno(aluno);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroAlunoActivity.this, "Aluno cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Toast.makeText(CadastroAlunoActivity.this, "Erro ao cadastrar aluno", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
