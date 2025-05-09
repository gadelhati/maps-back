CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO maps.geological_sample
(id, created_at, updated_at, cod_geological_sample, cod_station, area_origin, drag_velocity, drag_direction, drag_duration, photo_sample, photo_floor, side_scan, sample_type, sample_volume, sample_weight, sample_color, sample_texture, concretions, shells, plants, animals, pollution, principal_seabed, complementary_seabed, sample_seabed, preliminary_analysis, identification, sondagem, structure, sample_sequence, comprimento_testemunho, bottom_color, top_color, numnac, quality_control, selection_degree, rounding_degree)
SELECT uuid_generate_v4()::UUID, now(), now(), cod_amostr_geolog::INTEGER, cod_estacao::INTEGER, area_origem::INTEGER, arrasto_velocidade::INTEGER, arrasto_direcao::INTEGER, arrasto_duracao::FLOAT, CASE WHEN foto_amostra = 'S' THEN true WHEN foto_amostra = 'N' THEN false ELSE null END, CASE WHEN foto_fundo = 'S' THEN true WHEN foto_fundo = 'N' THEN false ELSE null END, side_scan, tipo_amostra::INTEGER, volume_amostra::FLOAT, peso_amostra::FLOAT, TRIM(cor_amostra), TRIM(textura_amostra), CASE WHEN concrecoes= 'S' THEN true WHEN concrecoes = 'N' THEN false ELSE null END, CASE WHEN conchas = 'S' THEN true WHEN conchas = 'N' THEN false ELSE null END, CASE WHEN plantas = 'S' THEN true WHEN plantas = 'N' THEN false ELSE null END, CASE WHEN animais = 'S' THEN true WHEN animais = 'N' THEN false ELSE null END, poluicao , tenca_principal::INTEGER, tenca_complementar::INTEGER, tenca_amostra::INTEGER, TRIM(obs_analise_preliminar), TRIM(obs_identificacao), sondagem::INTEGER, estrutura::INTEGER, TRIM(num_sec_amostra), comprimento_testemunho::INTEGER, cor_fundo::INTEGER, cor_topo::INTEGER, numnac::INTEGER, ctrlqc_geo::INTEGER, grau_selecao::INTEGER, grau_arredondamento::INTEGER
FROM sisbndo.tb_amostra_geologica ON CONFLICT DO NOTHING;

INSERT INTO maps.station_category
(id, created_at, updated_at, code, alias, name)
SELECT uuid_generate_v4()::UUID, now(), now(), cod_tipo_estacao::INTEGER, TRIM(nome_estacao), TRIM(descricao_estacao)
FROM sisbndo.tb_tipo_estacao ON CONFLICT DO NOTHING;

INSERT INTO maps.media_category
(id, created_at, updated_at, code, name)
SELECT uuid_generate_v4()::UUID, now(), now(), cod_tipo_midia::INTEGER, TRIM(nome_tipo_midia)
FROM sisbndo.tb_tipo_midia ON CONFLICT DO NOTHING;

INSERT INTO maps.platform_category
(id, created_at, updated_at, code, name)
SELECT uuid_generate_v4()::UUID, now(), now(), cod_tipo_plataforma::INTEGER, TRIM(nome_tipo_plataforma)
FROM sisbndo.tb_tipo_plataforma ON CONFLICT DO NOTHING;

INSERT INTO maps.module
(id, created_at, updated_at, name)
SELECT uuid_generate_v4()::UUID, now(), now(), TRIM(modulo_utilizacao)
FROM sisbndo.tb_tipo_equipamento ON CONFLICT DO NOTHING;

INSERT INTO maps.equipment_category
(id, created_at, updated_at, code, acronym, name, description, module)
SELECT uuid_generate_v4()::UUID, now(), now(), tte.cod_tipo_equipamento::INTEGER, TRIM(tte.acronimo), TRIM(tte.nome), TRIM(tte.descricao), m.id
FROM sisbndo.tb_tipo_equipamento tte LEFT JOIN maps.module m ON tte.modulo_utilizacao = m.name ON CONFLICT DO NOTHING;

INSERT INTO maps.datum
(id, created_at, updated_at, code, name, ae, umf)
SELECT uuid_generate_v4()::UUID, now(), now(), cod_datum, TRIM(datum), ae::INTEGER, um_f::FLOAT
FROM sisbndo.tb_datum ON CONFLICT DO NOTHING;

INSERT INTO maps.platform
(id, created_at, updated_at, code, telegraph_call_sign, international_call_sign, name, visual_call_sign, country)
SELECT uuid_generate_v4()::UUID, now(), now(), tp.cod_plataforma, TRIM(tp.indicativo_telegrafico ), TRIM(tp.indicativo_internacional), TRIM(tp.nome_plataforma), TRIM(tp.indicativo_visual), c.id
FROM sisbndo.tb_plataforma tp LEFT JOIN maps.country c ON tp.cod_pais = c.code ON CONFLICT DO NOTHING;

INSERT INTO maps.institution
(id, created_at, updated_at, code, name, mb, country)
SELECT uuid_generate_v4()::UUID, now(), now(), ti.cod_instituicao, TRIM(ti.nome_instituicao), CASE WHEN ti.mb = 'S' THEN true WHEN ti.mb = 'N' THEN false ELSE null END, c.id
FROM sisbndo.tb_instituicao ti LEFT JOIN maps.country c ON ti.cod_pais = c.code ON CONFLICT DO NOTHING;

INSERT INTO maps.media
(id, created_at, updated_at, code, identification, sequential, receipt, shipping, obs, institution, media_category)
SELECT uuid_generate_v4()::UUID, now(), now(), tm.cod_midia::INTEGER, TRIM(tm.identificacao), tm.sequencial::INTEGER, tm.data_recebimento, tm.data_remessa, TRIM(tm.obs), i.id, mc.id
FROM sisbndo.tb_midia tm LEFT JOIN maps.institution i ON tm.cod_instituicao = i.code::BIGINT LEFT JOIN maps.media_category mc ON tm.cod_tipo_midia = mc.code::BIGINT ON CONFLICT DO NOTHING;

INSERT INTO maps.project
(id, created_at, updated_at, code, name, description)
SELECT uuid_generate_v4()::UUID, now(), now(), cod_projeto::INTEGER, TRIM(nome_projeto), TRIM(descricao_projeto)
FROM sisbndo.tb_projeto ON CONFLICT DO NOTHING;

INSERT INTO maps.researcher
(id, created_at, updated_at, code, name, email, address)
SELECT uuid_generate_v4()::UUID, now(), now(), cod_pesquisador::INTEGER, TRIM(nome_pesquisador), TRIM(endereco_eletronico), TRIM(endereco_pesquisador)
FROM sisbndo.tb_pesquisador ON CONFLICT (id) DO NOTHING;

INSERT INTO maps.hydrographic_survey
(id, created_at, updated_at, code, name)
SELECT uuid_generate_v4()::UUID, now(), now(), cod_levantamento::INTEGER, TRIM(nome_levantamento)
FROM sisbndo.tb_levantamento ON CONFLICT DO NOTHING;

INSERT INTO maps.commission
(id, created_at, updated_at, code, cruise_name, cruise_number, name, description, start, finish, latitude_bottom_most, latitude_top_most, longitude_left_most, longitude_right_most, area_name, maximum_depth_area, minimum_depth_area, maximum_collection_depth, minimum_collection_depth, total_size_media, data_qualification, h_folder, obs, project, hydrographic_survey, coordinator, responsible, harbor_arrived, harbor_departure)
SELECT uuid_generate_v4()::UUID, now(), now(), tc.cod_comissao::INTEGER, TRIM(tc.nome_cruzeiro), TRIM(tc.num_cruzeiro), TRIM(tc.nome_comissao), TRIM(tc.descricao_comissao), tc.data_inicio, tc.data_fim, tc.lat_bottommost, tc.lat_topmost::FLOAT, tc.long_leftmost::FLOAT, tc.long_rightmost::FLOAT, tc.nome_area_comissao, tc.profundidade_maxima_area::FLOAT, tc.profundidade_minima_area::FLOAT, tc.profundidade_maxima_coleta::FLOAT, tc.profundidade_minima_coleta::FLOAT, tc.tamanho_total_midia::FLOAT, TRIM(tc.qualificacao_dados), TRIM(tc.pasta_h), TRIM(tc.obs), p.id::UUID, hsa.id::UUID, ic.id::UUID, ir.id::UUID, ha.id::UUID, hd.id::UUID
FROM sisbndo.tb_comissao tc
 LEFT JOIN maps.project p ON tc.cod_projeto = p.code::BIGINT
 LEFT JOIN maps.hydrographic_survey hsa ON tc.cod_levantamento = hsa.code::BIGINT
 LEFT JOIN maps.institution ic ON tc.cod_instituicao_coordenadora  = ic.code::BIGINT
 LEFT JOIN maps.institution ir ON tc.cod_instituicao_responsavel = ir.code::BIGINT
 LEFT JOIN maps.harbor ha ON tc.porto_chegada = ha.name
 LEFT JOIN maps.harbor hd ON tc.porto_partida = hd.name
ON CONFLICT DO NOTHING;
--num_cruzeiro: leg 8, null, espaço

INSERT INTO maps.harbor (id, created_at, updated_at, name)
SELECT
    uuid_generate_v4()::UUID,
    now(),
    now(),
    nome
FROM (
    SELECT DISTINCT initcap(TRIM(split_part(porto_chegada, '/', 1))) AS nome
    FROM sisbndo.tb_comissao
    WHERE porto_chegada IS NOT NULL

    UNION

    SELECT DISTINCT initcap(TRIM(split_part(porto_partida, '/', 1))) AS nome
    FROM sisbndo.tb_comissao
    WHERE porto_partida IS NOT NULL
) AS nomes
ON CONFLICT DO NOTHING;
--Rio e Rio De Janeiro

INSERT INTO maps.sample_method
(id, created_at, updated_at, name)
SELECT uuid_generate_v4()::UUID, now(), now(), nome
FROM (
	SELECT DISTINCT initcap(TRIM(metodo_amostragem)) AS nome
	FROM sisbndo.tb_equipamento te
	WHERE metodo_amostragem IS NOT NULL
) AS nomes
ON CONFLICT DO NOTHING;
--amostador/amostrador
--amostagem/amostragem

INSERT INTO maps.equipment
(id, created_at, updated_at, code, number, model, frequency, range, calibration, equipment_category, manufacturer, sample_method)
SELECT uuid_generate_v4()::UUID, now(), now(), te.cod_equipamento::INTEGER, TRIM(te.num_serie), TRIM(te.modelo), te.frequencia::INTEGER, te.alcance::INTEGER, te.ultima_calibracao, ec.id, m.id, sm.id
FROM sisbndo.tb_equipamento te
LEFT JOIN maps.equipment_category ec ON te.cod_tipo_equipamento = ec.code
LEFT JOIN maps.manufacturer m ON te.fabricante = m.name
LEFT JOIN maps.sample_method sm ON te.metodo_amostragem = sm.name
ON CONFLICT DO NOTHING;

INSERT INTO maps.media
(id, created_at, updated_at, code, institution, media_category, identification, sequential, receipt, shipping, obs)
SELECT uuid_generate_v4()::UUID, now(), now(), tm.cod_midia::INTEGER, i.id, mc.id, TRIM(tm.identificacao), tm.sequencial::INTEGER, tm.data_recebimento, tm.data_remessa, tm.obs
FROM sisbndo.tb_midia tm
LEFT JOIN maps.institution i ON tm.cod_instituicao = i.code::BIGINT
LEFT JOIN maps.media_category mc ON tm.cod_tipo_midia = mc.code
ON CONFLICT (id) DO NOTHING;

--tb_midia_equipamento      //só adiciona o bruto 7 tuplas com valor 1
--tb_plataforma_comissao    //
--tb_equipamento_comissao   //só replica a ultima calibração