
Vue.prototype.$http = axios;

var app = new Vue({
    el: '#vueApp',
    data: {
        message: 'result',
        deal_srl: '123',
        keyword: 'aa',
        keyword_org: 'bb',
        result: [{
            currentPage: '1',
            count: '10',
        }]
    },
    methods:{
        typing: function(e){
            this.text = e.target.value
        },
        handleSubmit: function(){
            var self = this;
            var formData = new FormData();
            formData.append("deal_srl", this.$data.deal_srl);
            formData.append("keyword", this.$data.keyword);
            formData.append("keyword_org", this.$data.keyword_org);


            this.$http.post('/form/create',
                formData
            )
                .then(function(response){
                    self.message = response.data;
                    app.getDealItems(self.result.currentPage, self.result.count);
                })
                .catch(function(error) {
                    if (error.response) {
                        self.message = 'error code : ' + error.response.status;
                    } else if (error.request) {
                        self.message = 'error request : ' + error.request;
                    }
                })
        },
        getDealItems: function(pageNum, countSize){
            var self = this;
            this.$http.get('/form/read', {
                params: {
                    page: pageNum,
                    count: countSize
                }
            })
                .then(function(response){
                    if(response.status === 200 && response.data.status === 'success'){
                        self.result = response.data;
                    }
                });

        },
        getIndex: function(index){
            return (index + this.result.currentPage - 5);
        }
    },
    mounted: function(){
        this.getDealItems(this.result.currentPage, this.result.count);
    }
});