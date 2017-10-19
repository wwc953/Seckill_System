/**
 * Created by Roll on 2017/10/19.
 */
//存放主要交互逻辑代码
//javascript 模块化
var seckill = {

    //封装秒杀相关的ajax的url
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        execution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },

    //获取秒杀地址，控制实现逻辑，执行秒杀
    handleSeckill: function (seckillId, node) {
        node.hide()
            .html('<button class="btn bg-primary btn-lg" id="killBtn">开始秒杀</button>');

        $.post(seckill.URL.exposer(seckillId), {}, function (result) {

            if (result && result['success']) {
                var exposer = result['data'];

                if (exposer['exposed']) {
                    //开启秒杀
                    //获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);

                    console.log("killUrl:" + killUrl);

                    //只绑定一次点击事件
                    $('#killBtn').one('click', function () {
                        //执行秒杀请求操作

                        //1、先禁用按钮
                        $(this).addClass('disabled');
                        //2、发送秒杀请求
                        $.post(killUrl, {}, function (result) {
                            console.log(result);
                            if (result && result['success']) {
                                console.log(result['data']);
                                var killResult = result['data'];
                                var status = killResult['status'];
                                var stateInfo = killResult['stateInfo'];
                                //3、显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    });
                    node.show();
                } else {
                    //未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countdown(seckillId, now, start, end);
                }
            } else {
                console.log('result : ' + result);
            }
        });
    },

    //时间判断，计时交互
    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        //时间判断
        if (nowTime > endTime) {
            seckillBox.html('秒杀已结束!');

        } else if (nowTime < startTime) {
            //秒杀未开始

            var killTime = new Date(startTime + 1000);

            seckillBox.countdown(killTime, function (event) {
                var format = event.strftime('秒杀开启时间： %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
                /*
                 时间完成后回调
                 */
            }).on('finish.countdown', function () {
                //获取秒杀地址，控制实现逻辑，执行秒杀
                seckill.handleSeckill(seckillId, seckillBox);
            });

        } else {
            //秒杀开始
            seckill.handleSeckill(seckillId, seckillBox);
        }

    },

    //验证手机号
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },

    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {

            //cookie中查找手机号
            var killPhone = $.cookie('killPhone');

            //模拟登录
            if (!seckill.validatePhone(killPhone)) {
                //绑定phone
                //控制输出
                var killPhoneModal = $('#killPhoneModal');

                //显示弹出层
                killPhoneModal.modal({
                    show: true, //显示弹出层
                    backdrop: "static", //禁止位置关闭
                    keyboard: false  //关闭键盘事件
                });

                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killphoneKey').val();
                    console.log('inputPhone : ' + inputPhone);

                    if (seckill.validatePhone(inputPhone)) {
                        //电话写入cookie
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        //刷新页面
                        window.location.reload();
                    } else {
                        $('#killphoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
            }

            //已经登录
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];

            //记时交互
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];

                    //时间判断，计时交互
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                } else {
                    //输出到浏览器控制台中
                    console.log('result : ' + result);
                }
            });
        }
    }
};