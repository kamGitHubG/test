CREATE TABLE NOTE
(
	id serial not null constraint note_pk primary key,
	text varchar not null
);

CREATE UNIQUE INDEX note_id_uindex ON NOTE(id);
