create table "Meteo" (
  id varchar(255) not null,
  city_id varchar(50) not null,
  lastRefresh timestamp not null,
  weather varchar(50) not null,
  primary key (id)
);