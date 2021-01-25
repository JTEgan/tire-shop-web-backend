INSERT INTO public.service(id, service_name, monthly_cost)
VALUES ('AV', 'Antivirus', null)
on conflict do nothing;
INSERT INTO public.service(id, service_name, monthly_cost)
VALUES ('CB', 'Cloudberry', 3.00)
on conflict do nothing;
INSERT INTO public.service(id, service_name, monthly_cost)
VALUES ('PSA', 'PSA', 2.00)
on conflict do nothing;
INSERT INTO public.service(id, service_name, monthly_cost)
VALUES ('TV', 'TeamViewer', 1.00)
on conflict do nothing;

INSERT INTO public.service_device_type_cost(service_id, device_type, monthly_cost)
values ('AV', 'WINDOWS_WORKSTATION', 5.00)
on conflict do nothing;
INSERT INTO public.service_device_type_cost(service_id, device_type, monthly_cost)
values ('AV', 'WINDOWS_SERVER', 5.00)
on conflict do nothing;
INSERT INTO public.service_device_type_cost(service_id, device_type, monthly_cost)
values ('AV', 'MAC', 7.00)
on conflict do nothing;

commit;