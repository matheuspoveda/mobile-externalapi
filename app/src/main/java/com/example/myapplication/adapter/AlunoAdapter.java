package com.example.myapplication.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Aluno;

import java.util.List;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {
    private List<Aluno> alunos;

    public AlunoAdapter(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aluno, parent, false);
        return new AlunoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = alunos.get(position);
        holder.textViewNome.setText(aluno.getNome());
        holder.textViewRa.setText(String.valueOf(aluno.getRa()));
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }

    public static class AlunoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome, textViewRa;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            textViewRa = itemView.findViewById(R.id.textViewRa);
        }
    }
}
