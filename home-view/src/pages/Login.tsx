/* eslint-disable no-console */
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Checkbox, Form, Input, message } from 'antd';
import axios from 'axios';
import qs from 'qs';
import React from 'react';
import { RouteComponentProps } from 'react-router-dom';
import styles from './Login.module.scss';

interface LoginProps extends RouteComponentProps {
  [key: string]: any;
}

const Login: React.FC<LoginProps> = ({ history }) => {
  const loginAuth = (values: any) => {
    if (values.remember === undefined) {
      values.remember = false;
    }
    axios({
      method: 'post',
      url: '/admin/login',
      headers: { 'content-type': 'application/x-www-form-urlencoded' }, // 请求头，发送FormData格式的数据，必须是 这种请求头。
      data: qs.stringify({
        username: values.username,
        password: values.password,
      }),
    }).then(res => {
      console.log(res.data);
      if (res.data.code === 0) {
        message.success(res.data.msg);
        history.push('/');
      }
    });
  };

  return (
    <div className={styles.wrap}>
      <Form
        className={styles.login_form}
        initialValues={{ remember: true }}
        onFinish={loginAuth}
      >
        <Form.Item>
          <h2 style={{ color: 'white' }}>Admin Login System</h2>
        </Form.Item>
        <Form.Item
          name="username"
          initialValue="admin"
          rules={[{ required: true, message: '请输入用户名' }]}
        >
          <Input
            prefix={<UserOutlined className={styles.form_item_icon} />}
            placeholder="用户名"
          />
        </Form.Item>
        <Form.Item
          name="password"
          initialValue="123456"
          rules={[{ required: true, message: '请输入密码!' }]}
        >
          <Input
            prefix={<LockOutlined className={styles.form_item_icon} />}
            type="password"
            placeholder="密码"
          />
        </Form.Item>
        <Form.Item>
          <Form.Item name="remember" valuePropName="checked" noStyle>
            <Checkbox style={{ color: 'white' }}>记住登陆</Checkbox>
            <a href="/login">忘记密码</a>
          </Form.Item>
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" block>
            登陆
          </Button>
          <span style={{ color: 'white' }}>或者</span>{' '}
          <a href="/login">现在注册</a>
        </Form.Item>
      </Form>
    </div>
  );
};

export default Login;
