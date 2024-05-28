package com.example.myapplication.network;
import com.example.myapplication.model.Aluno;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface AlunoService {
    @GET("aluno")
    Call<List<Aluno>> getAlunos();

    @POST("aluno")
    Call<Aluno> createAluno(@Body Aluno aluno);
}
