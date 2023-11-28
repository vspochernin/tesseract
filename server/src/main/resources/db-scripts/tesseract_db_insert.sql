-- Скрипт создания базы данных для Android приложения Tesseract
-- Заполнение таблиц
-- Заполняем таблицу компаний: 6 действующих компаний, с актуальной информацией из отчетов компаний
INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
  VALUES('Общество с ограниченной ответственностью «Самолет Плюс ЦФА»', 
		 'Компания ведет деятельность агенства недвижимости за вознограждение или на договорной основе и является  ' ||
		 'действующей, коммерческой. Общее количество направлений деятельнсоти - 4. Организация не имела налоговой ' ||
		 'задолженности по состоянию на 01.10.2022. Основным учредителем является компания ООО «Самолет Плюс», ' ||
		 'которая разрабатывает компьютерное ПО уже целых 17 лет.',
		'2022-09-05 00:00:00', 314750000, -60000, 1);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
  VALUES('Общество с ограниченной ответственностью «Цифровые активы»', 
		 'Компания ведет деятельность по управлению финансово-промышленными группами и является  действующей, ' ||
		 'коммерческой. Общее количество направлений деятельности - 4. Организация не имела налоговой задолженности ' ||
		 'по состоянию на 01.10.2021',
		'2015-11-24 00:00:00', 231550789000, -98575297000, 1);
		
INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
  VALUES('Акционерное общество «ДЖИ-ГРУПП»', 
		 'Компания ведет деятельность по консультированию по вопросам комерческой деятельности и управления, ' ||
		 'является  действующей, коммерческой. Компания занимает первое место в Республике Татарстан по объему ' ||
		 'ввода жилья за 2022 год и 34 место по всей России. Организация не имела налоговой задолженности по ' ||
		 'состоянию на 01.10.2022',
		'2021-02-19 00:00:00', 227365000, 1511000, 29);
		
INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
  VALUES('Общество с ограниченной ответственностью «НТС «Градиент»', 
		 'Компания ведет деятельность по производству парфюмерных и косметических средств, является  ' ||
		 'действующей, коммерческой. Общее количество направлений деятельности - 4. Организация не имела ' ||
		 'налоговой задолженности по состоянию на 01.10.2021',
		'1995-05-15 00:00:00', 20627249000, 1253870000, 579);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
  VALUES('Общество ограниченной ответственностью «Глобал Факторинг Нетворк Рус»', 
		 'Федеральная факторинговая компания, занимающаяся оперативным финансированием оборотного капитала ' ||
		 'предприятий под уступку надежной краткосрочной дебиторской задолженности с использованием юридически ' ||
		 'значимого электронного документооборота. Компания входит в топ-30 факторинговых компаний России, в ' ||
		 'топ-10 по сделкам с предприятиями малого и среднего бизнеса',
		'2011-03-28 00:00:00', 1477775000, 16404000, 7);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
  VALUES('Общество с ограниченной ответственностью «Лизинговая компания Простые решения»',
		 'Лизинговая компания, с 2011 года предоставляющая в лизинг широкий спектр имущества: от ' ||
		 'легкового транспорта до высокотехнологичного оборудования и недвижимости, в том числе — ' ||
		 'финансирование импорта. Организация не имела налоговой задолженности по состоянию на 01.10.2021',
		'2011-08-17 00:00:00', 1536915000, 118383000, 78);

-- Заполняем таблицу активов: 8 актуальных активов, с актуальной информацией (минимум один актив на компанию)
INSERT INTO assets(title, description, release_datetime, company_id, interest)
  VALUES('NDM_18',
		 'Количество выпускаемых ЦФА: 5 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
  'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 10 000 рублей Российской Федерации. ' ||
  'Выплаты производятся ежемесячно',
		'2023-11-21 00:00:00', 5, 18);
		
INSERT INTO assets(title, description, release_datetime, company_id, interest)
  VALUES('NDM_17',
		 'Количество выпускаемых ЦФА: 1 штука. Дробление ЦФА и досрочное погашение не предусмотрено. Каждый ЦФА' ||
		 ' имеет фиксированную номинальную стоимость в размере 100 000 000 рублей Российской Федерации. Выплаты ' ||
		 'производятся ежемесячно',
		'2023-11-16 00:00:00', 4, 18.4);

INSERT INTO assets(title, description, release_datetime, company_id, interest)
  VALUES('NDM_13',
		 'Количество выпускаемых ЦФА: 50 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
		 'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 1000 рублей Российской Федерации. ' ||
		 'Выплаты производятся ежемесячно',
		'2023-10-18 00:00:00', 6, 14);

INSERT INTO assets(title, description, release_datetime, company_id, interest)
  VALUES('NDM_15',
		 'Количество выпускаемых ЦФА: 10 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
		 'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 10 000 рублей Российской Федерации. ' ||
		 'Выплаты производятся ежемесячно',
		'2023-11-21 00:00:00', 5, 18);
		
INSERT INTO assets(title, description, release_datetime, company_id, interest)
  VALUES('MINETOKEN_53',
		 'Лимит привлекаемых Эмитентом денежных средств составляет 150 000 000 рублей. Дробление ЦФА ' ||
		 'предусматривается до сотых, досрочное погашение не предусмотрено. ЦФА не имеют фиксированной ' ||
		 'номинальной стоимости. ЦФА удостоверяет права обладателя на получение периодических выплат в размере,' ||
		 ' равном суммам дивидендов на одну обыкновенную акцию ПАО «ГМК «Норильский никель»',
		'2023-11-24 00:00:00', 1, 8.8);

INSERT INTO assets(title, description, release_datetime, company_id, interest)
  VALUES('MINETOKEN_29',
		 'Лимит привлекаемых Эмитентом денежных средств составляет 150 000 000 рублей. Дробление ЦФА' ||
		 ' предусматривается до сотых, досрочное погашение не предусмотрено. ЦФА не имеют фиксированной ' ||
		 'номинальной стоимости. ЦФА удостоверяет права обладателя на получение периодических выплат в размере,' ||
		 ' равном суммам дивидендов на одну обыкновенную акцию ПАО «ГМК «Норильский никель»',
		'2023-09-25 00:00:00', 1, 9.3);

INSERT INTO assets(title, description, release_datetime, company_id, interest)
  VALUES('NDM_9',
		 'Количество выпускаемых ЦФА: 510 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
		 'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 99 300 рублей Российской Федерации. ' ||
		 'Выплата 1 раз в дату погашения',
		'2023-08-04 00:00:00', 3, 10);
		
INSERT INTO assets(title, description, release_datetime, company_id, interest)
  VALUES('NDM_10',
		 'Количество выпускаемых ЦФА: 5 штук. Дробление ЦФА до десятых, досрочное погашение не предусмотрено. ' ||
		 'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 230 000 рублей Российской Федерации. ' ||
		 'Выплата 1 раз в дату погашения',
		'2023-07-24 00:00:00', 2, 15);	

-- Заполняем таблицу цен активов
INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(1, 10000, '2023-11-21 00:00:00');
  
-- цена на актив 1 понизилась
INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(1, 9900, '2023-11-23 00:00:00');
  
INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(2, 100000, '2023-11-23 00:00:00');
  
INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(3, 1000, '2023-10-18 00:00:00');
  
INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(3, 1100, '2023-11-18 00:00:00');

INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(4, 10000, '2023-11-21 00:00:00');
  
INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(5, 17300, '2023-11-24 00:00:00');
  
INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(6, 15900, '2023-09-25 00:00:00');

INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(6, 17450, '2023-10-25 00:00:00');

INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(6, 17300, '2023-11-23 00:00:00');
  
INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(7, 99300, '2023-08-04 00:00:00');

INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(7, 99700, '2023-09-04 00:00:00');

INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(7, 99000, '2023-10-04 00:00:00');

INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(7, 99800, '2023-11-04 00:00:00');
  
INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(8, 230000, '2023-07-24 00:00:00');
  
INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(8, 230200, '2023-08-24 00:00:00');
  
INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(8, 229000, '2023-09-24 00:00:00');
  
INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(8, 230900, '2023-10-24 00:00:00');
  
INSERT INTO prices(asset_id, price, set_datetime)
  VALUES(8, 231100, '2023-11-24 00:00:00');
  
INSERT INTO risks(description)
  VALUES('Высокорискованная. Состоящая из активов, имеющих высокий риск');
  
INSERT INTO risks(description)
  VALUES('Среднерискованная. Состоящая из активов, имеющих средний риск');
  
INSERT INTO risks(description)
  VALUES('Низкорискованная. Состоящая из активов, имеющих низкий риск');
  
INSERT INTO risks(description)
  VALUES('Комбинированная. Состоящая из высоко, средне и низкорискованных активов');
  
--два пользователя, один будет пустым (нет ни избранных, ни диверсификацией), а 
--другой наполненным (несколько избранных и хотя бы одна диверсификация).
-- пустой
INSERT INTO users(login, email, password)
  VALUES('vspochernin', 'vspochernin@mail.ru', 'd404559f602eab6fd602ac7680dacbfaadd13630335e951f097af3900e9de176b6db28512f2e000b9d04fba5133e8b1c6e8df59db3a8ab9d60be4b97cc9e81db');

--полный (будет две диверсификации + четыре избранных)
INSERT INTO users(login, email, password)
  VALUES('vrazukrantov', 'vrazbusiness@mail.ru', 'd404559f602eab6fd602ac7680dacbfaadd13630335e951f097af3900e9de176b6db28512f2e000b9d04fba5133e8b1c6e8df59db3a8ab9d60be4b97cc9e81db');

-- Добавляем данные в таблицу связи пользователей и активов (избранные)
INSERT INTO users_assets(user_id, asset_id)
  VALUES(2, 1);
  
INSERT INTO users_assets(user_id, asset_id)
  VALUES(2, 5);

INSERT INTO users_assets(user_id, asset_id)
  VALUES(2, 8);
  
INSERT INTO users_assets(user_id, asset_id)
  VALUES(2, 2);

-- Добавляем данные в таблицу диверсификаций diversifications 
-- диверсификация: комбинированная
INSERT INTO diversifications(user_id, create_datetime, risk_id, amount)
  VALUES(2, '2023-11-23 00:00:00', 4, 50000);
  
-- update diversifications set amount = 100000 where id=2
-- диверсификация: низкорискованная
INSERT INTO diversifications(user_id, create_datetime, risk_id, amount)
  VALUES(2, '2023-11-23 00:00:00', 3, 150000);

-- Заполняем таблицу активов в диверсификации
-- первая: три актива 1, десять активов 3, один актив 4
INSERT INTO diversifications_assets(diversification_id, asset_id, count)
  VALUES(1, 1, 3);

INSERT INTO diversifications_assets(diversification_id, asset_id, count)
  VALUES(1, 3, 10);

INSERT INTO diversifications_assets(diversification_id, asset_id, count)
  VALUES(1, 4, 1);
  
-- вторая: один актив 7, два актива 1, три актива 4
INSERT INTO diversifications_assets(diversification_id, asset_id, count)
  VALUES(2, 7, 1);

INSERT INTO diversifications_assets(diversification_id, asset_id, count)
  VALUES(2, 1, 2);
  
INSERT INTO diversifications_assets(diversification_id, asset_id, count)
  VALUES(2, 4, 3);
