package dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Conta;
import modelo.Cotacao;
import modelo.Habilitado;
import modelo.ItemCotacao;
import modelo.Oferta;
import modelo.Resposta;


public class CotacaoMapper {
	private Cotacao cotacao;

	public CotacaoMapper(Cotacao cotacao) {
		this.cotacao = cotacao;
	}

	public Map<String, Object> mapearCotador() {
		if (cotacao == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> itens = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> habilitados = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> respostas = new ArrayList<Map<String, Object>>();

		map.put("id", cotacao.getId());
		map.put("assunto", cotacao.getAssunto());
		map.put("dataPublicacao", formatarData(cotacao.getDataPublicacao()));
		map.put("dataFechamento", formatarData(cotacao.getDataFechamento()));
		if (cotacao.getDataEncerramento() != null) {
			map.put("dataEncerramento", formatarData(cotacao.getDataEncerramento()));
		}
		map.put("situacao", cotacao.getSituacao());
		map.put("itens", itens);
		map.put("habilitados", habilitados);
		map.put("respostas", respostas);

		for (ItemCotacao item: cotacao.getItens()) {
			Map<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("id", item.getId());
			itemMap.put("descricao", item.getDescricao());
			itemMap.put("quantidade", item.getQuantidade());
			itens.add(itemMap);
		}

		for (Habilitado h: cotacao.getHabilitados()) {
			Map<String, Object> habilitadoMap = new HashMap<String, Object>();
			habilitadoMap.put("id", h.getId());
			habilitadoMap.put("contato", h.getContato().toMap());
			habilitados.add(habilitadoMap);

			if (h.getRespostas() != null) {
				for (Resposta r: h.getRespostas()) {
					Map<String, Object> respostaMap = new HashMap<String, Object>();
					respostaMap.put("habilitadoId", r.getHabilitado().getId());
					if (r.getDataEnvio() != null) {
						respostaMap.put("dataEnvio", formatarData(r.getDataEnvio()));
					}
					respostaMap.put("observacao", r.getObservacao());
					List<Map<String, Object>> ofertas = new ArrayList<Map<String,Object>>();
					for (Oferta o: r.getOfertas()) {
						Map<String, Object> ofertaMap = new HashMap<String, Object>();
						ofertaMap.put("id", o.getId());
						ofertaMap.put("itemId", o.getItemCotacao().getId());
						ofertaMap.put("valor", o.getValor());
						ofertas.add(ofertaMap);
					}
					if (ofertas.size() > 0) {
						respostaMap.put("ofertas", ofertas);
					}
					respostas.add(respostaMap);
				}
			}
		}

		return map;
	}

	public Map<String, Object> mapearCotadorLista() {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", cotacao.getId());
		map.put("assunto", cotacao.getAssunto());
		map.put("dataPublicacao", formatarData(cotacao.getDataPublicacao()));
		map.put("dataFechamento", formatarData(cotacao.getDataFechamento()));
		if (cotacao.getDataEncerramento() != null) {
			map.put("dataEncerramento", formatarData(cotacao.getDataEncerramento()));
		}
		map.put("situacao", cotacao.getSituacao());
		map.put("itens", cotacao.getItens().size());

		return map;
	}

	public Map<String, Object> mapearHabilitado(Conta conta) throws Exception {
		if (cotacao == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> itens = new ArrayList<Map<String, Object>>();
		Map<String, Object> respostaMap = new HashMap<String, Object>();
		map.put("id", cotacao.getId());
		map.put("assunto", cotacao.getAssunto());
		map.put("dataPublicacao", formatarData(cotacao.getDataPublicacao()));
		map.put("dataFechamento", formatarData(cotacao.getDataFechamento()));
		if (cotacao.getDataEncerramento() != null) {
			map.put("dataEncerramento", formatarData(cotacao.getDataEncerramento()));
		}
		map.put("situacao", cotacao.getSituacao());
		map.put("itens", itens);
		map.put("resposta", respostaMap);

		for (ItemCotacao item: cotacao.getItens()) {
			Map<String, Object> itemMap = new HashMap<String, Object>();
			itemMap.put("id", item.getId());
			itemMap.put("descricao", item.getDescricao());
			itemMap.put("quantidade", item.getQuantidade());
			itens.add(itemMap);
		}

		Habilitado habilitado = cotacao.getHabilitado(conta);
		if (habilitado != null && habilitado.getRespostas() != null && !habilitado.getRespostas().isEmpty()) {
			Resposta r = habilitado.getRespostas().iterator().next();
			respostaMap.put("id", r.getId());
			respostaMap.put("dataEnvio", formatarData(r.getDataEnvio()));
			respostaMap.put("observacao", r.getObservacao());
			if (r.getOfertas() != null) {
				List<Map<String, Object>> ofertas = new ArrayList<Map<String,Object>>();
				for (Oferta o: r.getOfertas()) {
					Map<String, Object> ofertaMap = new HashMap<String, Object>();
					ofertaMap.put("id", o.getId());
					ofertaMap.put("itemId", o.getItemCotacao().getId());
					ofertaMap.put("valor", o.getValor());
					ofertas.add(ofertaMap);
				}
				respostaMap.put("ofertas", ofertas);
			}
		}
		return map;
	}

	private String formatarData(Date data) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return format.format(data).substring(8, 10).concat("/")
				.concat(format.format(data).substring(5, 7)).concat("/")
				.concat(format.format(data).substring(0, 4)).concat(" ")
				.concat(format.format(data).substring(11));
		
	}
}