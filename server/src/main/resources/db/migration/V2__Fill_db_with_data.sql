INSERT INTO operators(title, inclusion_datetime)
VALUES ('ООО "ГалактикСофт"', '2022-02-03 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('ПАО "КвантБанк"', '2022-03-17 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('ООО "ТехноСвет"', '2022-03-17 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('АО "ФьючерТек"', '2023-02-02 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('ООО "Блокчейн Солюшнс"', '2023-03-09 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('ООО "ИнвестТокен"', '2023-06-15 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('АКБ "ГлобалФинанс" (АО)', '2023-06-15 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('ПАО "ЭксчейнжМаркет"', '2023-06-22 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('ООО "КриптоХаб"', '2023-07-27 00:00:00+03');

INSERT INTO operators(title, inclusion_datetime)
VALUES ('НКО "ФинДепозитарий" (АО)', '2023-08-03 00:00:00+03');


INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('ООО "Фантазия Инвест"',
        'Специализируется на разработке и внедрении инновационных финансовых технологий. Имеет широкий спектр инвестиционных проектов в различных отраслях экономики. Полностью соответствует современным требованиям цифровой экономики.',
        '2015-11-24 00:00:00+03', 23155078900000, -9857529700000, 1);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('ООО "МегаБилд"',
        'Оказывает полный спектр услуг в области строительства и недвижимости. Известна своими инновационными подходами к строительству и управлению объектами недвижимости.',
        '2022-09-05 00:00:00+03', 31475000000, -6000000, 1);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('АО "Эко-Инновации"',
        'Занимается разработкой и реализацией проектов в области экологии и устойчивого развития. Продукция компании помогает снижать воздействие на окружающую среду и повышать эффективность использования ресурсов.',
        '2021-02-19 00:00:00+03', 22736500000, 151100000, 29);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('ООО "КосмоТех"',
        'Разрабатывает высокотехнологичное оборудование и программное обеспечение для космической отрасли. Ведет активное сотрудничество с ведущими научными центрами и университетами.',
        '1995-05-15 00:00:00+03', 2062724900000, 125387000000, 579);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('ООО "Финансовые Решения"',
        'Предоставляет широкий спектр финансовых услуг для бизнеса, включая факторинг, лизинг и кредитование. Имеет разветвленную сеть филиалов и представительств в крупных городах.',
        '2011-03-28 00:00:00+03', 147777500000, 1640400000, 7);

INSERT INTO companies(title, description, foundation_datetime, revenue, profit, staff)
VALUES ('ООО "ТрансТехЛизинг"',
        'Специализируется на предоставлении в лизинг транспортных средств и спецтехники для крупного и среднего бизнеса. Использует индивидуальный подход к каждому клиенту, предлагая оптимальные условия сотрудничества.',
        '2011-08-17 00:00:00+03', 153691500000, 11838300000, 78);


INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_18',
        'Количество выпускаемых ЦФА: 5 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 10 000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-11-21 00:00:00+03', 5, 18, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_17',
        'Количество выпускаемых ЦФА: 1 штука. Дробление ЦФА и досрочное погашение не предусмотрено. Каждый ЦФА' ||
        ' имеет фиксированную номинальную стоимость в размере 100 000 000 рублей Российской Федерации. Выплаты ' ||
        'производятся ежемесячно',
        '2023-11-16 00:00:00+03', 4, 18.4, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_12',
        'Количество выпускаемых ЦФА: 1 штука. Дробление ЦФА и досрочное погашение не предусмотрено. Каждый ЦФА' ||
        ' имеет фиксированную номинальную стоимость в размере 100 000 000 рублей Российской Федерации. Выплаты ' ||
        'производятся ежемесячно',
        '2023-12-10 00:00:00+03', 4, 18.4, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_13',
        'Количество выпускаемых ЦФА: 50 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 1000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-10-18 00:00:00+03', 6, 14, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_43',
        'Количество выпускаемых ЦФА: 100 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 3000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-11-02 00:00:00+03', 6, 15, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_33',
        'Количество выпускаемых ЦФА: 20 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 4000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-09-12 00:00:00+03', 6, 13.5, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_53',
        'Количество выпускаемых ЦФА: 10 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 10 000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-11-20 00:00:00+03', 6, 15.5, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_15',
        'Количество выпускаемых ЦФА: 10 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 10 000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-11-21 00:00:00+03', 5, 18, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_25',
        'Количество выпускаемых ЦФА: 50 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 20 000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-08-11 00:00:00+03', 5, 16.5, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_5',
        'Количество выпускаемых ЦФА: 30 000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 5000 рублей Российской Федерации. ' ||
        'Выплаты производятся ежемесячно',
        '2023-10-10 00:00:00+03', 5, 17, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('MINETOKEN_53',
        'Лимит привлекаемых Эмитентом денежных средств составляет 150 000 000 рублей. Дробление ЦФА ' ||
        'предусматривается до сотых, досрочное погашение не предусмотрено. ЦФА не имеют фиксированной ' ||
        'номинальной стоимости. ЦФА удостоверяет права обладателя на получение периодических выплат в размере,' ||
        ' равном суммам дивидендов на одну обыкновенную акцию ПАО «ГМК «Норильский никель»',
        '2023-11-24 00:00:00+03', 1, 8.8, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('MINETOKEN_29',
        'Лимит привлекаемых Эмитентом денежных средств составляет 150 000 000 рублей. Дробление ЦФА' ||
        ' предусматривается до сотых, досрочное погашение не предусмотрено. ЦФА не имеют фиксированной ' ||
        'номинальной стоимости. ЦФА удостоверяет права обладателя на получение периодических выплат в размере,' ||
        ' равном суммам дивидендов на одну обыкновенную акцию ПАО «ГМК «Норильский никель»',
        '2023-09-25 00:00:00+03', 1, 9.3, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('MINETOKEN_51',
        'Лимит привлекаемых Эмитентом денежных средств составляет 150 000 000 рублей. Дробление ЦФА' ||
        ' предусматривается до сотых, досрочное погашение не предусмотрено. ЦФА не имеют фиксированной ' ||
        'номинальной стоимости. ЦФА удостоверяет права обладателя на получение периодических выплат в размере,' ||
        ' равном суммам дивидендов на одну обыкновенную акцию ПАО «ГМК «Норильский никель»',
        '2023-11-23 00:00:00+03', 1, 8.9, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('MINETOKEN_17',
        'Лимит привлекаемых Эмитентом денежных средств составляет 150 000 000 рублей. Дробление ЦФА' ||
        ' предусматривается до сотых, досрочное погашение не предусмотрено. ЦФА не имеют фиксированной ' ||
        'номинальной стоимости. ЦФА удостоверяет права обладателя на получение периодических выплат в размере,' ||
        ' равном суммам дивидендов на одну обыкновенную акцию ПАО «ГМК «Норильский никель»',
        '2023-08-17 00:00:00+03', 1, 9.5, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_9',
        'Количество выпускаемых ЦФА: 510 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 99 300 рублей Российской Федерации. ' ||
        'Выплата 1 раз в дату погашения',
        '2023-08-04 00:00:00+03', 3, 10, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_19',
        'Количество выпускаемых ЦФА: 1000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 80 500 рублей Российской Федерации. ' ||
        'Выплата 1 раз в дату погашения',
        '2023-09-15 00:00:00+03', 3, 12, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_29',
        'Количество выпускаемых ЦФА: 2000 штук. Дробление ЦФА и досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 15 800 рублей Российской Федерации. ' ||
        'Выплата 1 раз в дату погашения',
        '2023-11-05 00:00:00+03', 3, 13.5, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_10',
        'Количество выпускаемых ЦФА: 5 штук. Дробление ЦФА до десятых, досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 230 000 рублей Российской Федерации. ' ||
        'Выплата 1 раз в дату погашения',
        '2023-07-24 00:00:00+03', 2, 15, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_2',
        'Количество выпускаемых ЦФА: 5 штук. Дробление ЦФА до десятых, досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 230 600 рублей Российской Федерации. ' ||
        'Выплата 1 раз в дату погашения',
        '2023-05-11 00:00:00+03', 2, 15, 1);

INSERT INTO assets(title, description, release_datetime, company_id, interest, operator_id)
VALUES ('NDM_20',
        'Количество выпускаемых ЦФА: 50 штук. Дробление ЦФА до десятых, досрочное погашение не предусмотрено. ' ||
        'Каждый ЦФА имеет фиксированную номинальную стоимость в размере 50 500 рублей Российской Федерации. ' ||
        'Выплата 1 раз в дату погашения',
        '2023-07-12 00:00:00+03', 2, 14.5, 1);


INSERT INTO users(login, email, password)
VALUES ('vspochernin', 'vspochernin@gmail.com', '$2a$10$SGRw6G/5NR5WBIRJjq2HJuVFQSJsfK.dDjPx2pWYDENOWUOXCDoVy');


INSERT INTO users(login, email, password)
VALUES ('vrazukrantov', 'vrazbusiness@mail.ru', '$2a$10$SGRw6G/5NR5WBIRJjq2HJuVFQSJsfK.dDjPx2pWYDENOWUOXCDoVy');


INSERT INTO users_assets(user_id, asset_id)
VALUES (2, 1);

INSERT INTO users_assets(user_id, asset_id)
VALUES (2, 5);

INSERT INTO users_assets(user_id, asset_id)
VALUES (2, 8);

INSERT INTO users_assets(user_id, asset_id)
VALUES (2, 2);


INSERT INTO diversifications(user_id, create_datetime, risk_type_id, amount)
VALUES (1, '2023-12-27 09:00:00+03', 3, 56432017);


INSERT INTO diversifications(user_id, create_datetime, risk_type_id, amount)
VALUES (2, '2023-12-28 09:00:00+03', 2, 39874008);


INSERT INTO diversifications_assets(diversification_id, asset_id, count)
VALUES (1, 1, 3);

INSERT INTO diversifications_assets(diversification_id, asset_id, count)
VALUES (1, 3, 10);

INSERT INTO diversifications_assets(diversification_id, asset_id, count)
VALUES (1, 4, 1);

INSERT INTO diversifications_assets(diversification_id, asset_id, count)
VALUES (2, 7, 1);

INSERT INTO diversifications_assets(diversification_id, asset_id, count)
VALUES (2, 1, 2);

INSERT INTO diversifications_assets(diversification_id, asset_id, count)
VALUES (2, 4, 3);
