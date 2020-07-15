.class Lcom/huanozong/sale/util/LoginActivity$1;
.super Landroid/os/Handler;
.source "LoginActivity.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/huanozong/sale/util/LoginActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/huanozong/sale/util/LoginActivity;


# direct methods
.method constructor <init>(Lcom/huanozong/sale/util/LoginActivity;)V
    .registers 2
    .param p1, "this$0"    # Lcom/huanozong/sale/util/LoginActivity;

    .prologue
    .line 14
    iput-object p1, p0, Lcom/huanozong/sale/util/LoginActivity$1;->this$0:Lcom/huanozong/sale/util/LoginActivity;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .registers 4
    .param p1, "paramAnonymousMessage"    # Landroid/os/Message;

    .prologue
    .line 17
    new-instance v0, Landroid/app/AlertDialog$Builder;

    iget-object v1, p0, Lcom/huanozong/sale/util/LoginActivity$1;->this$0:Lcom/huanozong/sale/util/LoginActivity;

    invoke-direct {v0, v1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    const-string v1, "\ufffd\ufffd\ufffd\ufffd"

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const-string v1, "\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffdQQ 5132709"

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    const v1, 0x7f070063

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setIcon(I)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    new-instance v1, Lcom/huanozong/sale/util/LoginActivity$1$1;

    invoke-direct {v1, p0}, Lcom/huanozong/sale/util/LoginActivity$1$1;-><init>(Lcom/huanozong/sale/util/LoginActivity$1;)V

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setOnDismissListener(Landroid/content/DialogInterface$OnDismissListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 23
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 24
    return-void
.end method
