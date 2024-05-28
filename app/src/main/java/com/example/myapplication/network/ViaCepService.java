package com.example.myapplication.network;
import com.example.myapplication.model.Endereco;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepService {
    @GET("ws/{cep}/json/")
    Call<Endereco> getEndereco(@Path("cep") String cep);
}
