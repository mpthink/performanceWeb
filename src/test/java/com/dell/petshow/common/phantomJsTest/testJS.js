var page = require('webpage').create(),
    system = require('system'),
    address, output, size;

if (system.args.length < 3 || system.args.length > 5) {
    phantom.exit(1);
} else {
    address = system.args[1];//传入url地址
    output = system.args[2];//输出图片的地址
    page.viewportSize = { width: 1500, height: 500 };//自定义定义宽高

    page.open(address, function (status) {
        if (status !== 'success') {
            console.log('Unable to load the address!');
            phantom.exit(1);
        } else {
            window.setTimeout(function () {
                length = page.evaluate(function () {
                    //此函数在目标页面执行的，上下文环境非本phantomjs，所以不能用到这个js中其他变量
                    var div = document.getElementById('main'); //要截图的div的id
                    var bc = div.getBoundingClientRect();
                    var top = bc.top;
                    var left = bc.left;
                    var width = bc.width;
                    var height = bc.height;
                    window.scrollTo(0, 10000);//滚动到底部
                    return [top, left, width, height];
                });

                page.clipRect = { //截图的偏移和宽高
                    top: length[0],
                    left: length[1],
                    width: length[2],
                    height: length[3]
                };

                page.render(output);
                phantom.exit();
            }, 5000);
        }
    });
}