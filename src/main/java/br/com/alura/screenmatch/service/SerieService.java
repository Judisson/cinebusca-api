package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repositorio;

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(repositorio.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converteDados(repositorio.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(repositorio.lancamentosMaisRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serieEncontrada = repositorio.findById(id);

        if(serieEncontrada.isPresent()) {
            Serie serie = serieEncontrada.get();
            return new SerieDTO(serie.getId(), serie.getTitulo(), serie.getTotalTemporadas(), serie.getAvaliacao(), serie.getGenero(), serie.getAtores(), serie.getPoster(), serie.getSinopse());
        }
        return null;
    }

    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream()
                .map(serie -> new SerieDTO(serie.getId(), serie.getTitulo(), serie.getTotalTemporadas(), serie.getAvaliacao(), serie.getGenero(), serie.getAtores(), serie.getPoster(), serie.getSinopse()))
                .collect(Collectors.toList());
    }
}
