import React, { FC, useEffect, useState } from 'react';
import axios from 'axios';
import { Timeline } from 'antd';
import { ClockCircleOutlined } from '@ant-design/icons';

import Nav from './../nav/Nav';

// interface Detail {
//     name: string;
//     age: number;
//     location: string;
//   }

const Demo: FC = () => {
    // const [] = useState([{ 'title': '', 'content': 'content', 'createTime': '2020-01-01' }]);
    const [jsonData, setjsonData] = useState([{ 'title': '', 'content': 'content', 'createTime': '2020-01-01' }])
    useEffect(() => {
        axios({
            method: 'get',
            url: 'https://admin.gbhome.com/api/v4/common/3in1/subscribe',
            params: {
                pageNum: 1,
                pageSize: 10
            }
        }).then(res => {
            setjsonData(res.data.data.records)
        })
    }, []);

    const listItems = jsonData.map((item) =>
        <Timeline.Item >
            <p>{item.title}</p>
            <p>{item.content}</p>
            <p>{item.createTime}</p>
        </Timeline.Item>
    );
    return (
        <div>
            <Nav />
            <Timeline mode="alternate">
                {listItems}
            </Timeline>
        </div>
    );
}




export default Demo;


