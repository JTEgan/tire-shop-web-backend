
INSERT INTO public.service(id, service_name) VALUES ('AV', 'Antivirus') on conflict do nothing;
INSERT INTO public.service(id, service_name) VALUES ('CB', 'Cloudberry') on conflict do nothing;
INSERT INTO public.service(id, service_name) VALUES ('PSA', 'PSA') on conflict do nothing;
INSERT INTO public.service(id, service_name) VALUES ('TV', 'TeamViewer') on conflict do nothing;

commit;