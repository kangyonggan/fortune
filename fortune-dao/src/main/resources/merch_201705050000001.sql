# 商户 319879465156486 的初始化数据
USE fortune_dev;
INSERT INTO merchant
(merch_co, merch_nm, merch_acct_no, merch_acct_nm, balance, merch_mobile, merch_id_no)
VALUES
  ('201705050000001', '公测商户', '6228216660054088518', '康永敢', 10000000, '15121119571', '340321199103173095');

INSERT INTO trans
(merch_co, tran_co, tran_nm, sing_quota, date_quota)
VALUES
  ('201705050000001', 'K001', '签约', -1, -1),
  ('201705050000001', 'K002', '解约', -1, -1),
  ('201705050000001', 'K003', '单笔代扣', 10000000, 10000000),
  ('201705050000001', 'K004', '单笔代付', 10000000, 10000000),
  ('201705050000001', 'K005', '交易查询', -1, -1),
  ('201705050000001', 'K006', '账户余额查询', -1, -1);